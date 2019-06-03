package com.zigerianos.jourtrip.presentation.scenes.home

import com.google.gson.Gson
import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.ErrorResponse
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.ServiceError
import com.zigerianos.jourtrip.domain.usecases.GetTimeLineUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import retrofit2.HttpException
import timber.log.Timber

class HomePresenter(
    private val authManager: AuthManager,
    private val getTimeLineUseCase: GetTimeLineUseCase,
    private val gson: Gson
) : BasePresenter<IHomePresenter.IHomeView>(), IHomePresenter {

    private var mCommentList: List<Comment> = emptyList()

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        requestTimeLine()
    }

    override fun locationClicked(location: Location) {
        getMvpView()?.navigateToLocationDetail(location)
    }

    private fun requestTimeLine() {
        authManager.getUserId()?.let { userId ->
            val disposable = getTimeLineUseCase.observable(GetTimeLineUseCase.Params(userId))
                .subscribe({ commentsList ->

                    if (commentsList.isEmpty()) {
                        //TODO: IMPLEMENTAR LISTA VACIA
                        getMvpView()?.stateData()
                        return@subscribe
                    }

                    mCommentList = commentsList
                    getMvpView()?.loadComments(mCommentList)
                    getMvpView()?.stateData()

                }, {
                    Timber.e(it)

                    val error = it as HttpException
                    error.response().errorBody()?.let { errorResponse ->
                        val serviceError = gson.fromJson(errorResponse.string().toString(), ErrorResponse::class.java)

                        when (ServiceError.getServiceError(serviceError.error)) {
                            ServiceError.TOKEN_EXPIRED, ServiceError.UNAUTHORIZED, ServiceError.CREDENTIALS_INVALID -> {
                                Timber.e("ServiceError: TOKEN_EXPIRED or UNAUTHORIZED")
                                authManager.deleteUser()
                                getMvpView()?.navigateToInit()
                                return@subscribe
                            }
                            ServiceError.NOT_FOUND, ServiceError.UNKNOWN -> {
                                Timber.e("ServiceError: NOT_FOUND or UNKNOWN")
                                getMvpView()?.stateError()
                                return@subscribe
                            }
                        }
                    }
                })

            addDisposable(disposable)
        }
    }

    /*fun fetchDeadline(city: String) {
        getMvpView()?.stateLoading()

        val params = GetLocationsByCityUseCase.Params(city)

        val disposable = getLocationsByCityUseCase.observable(params)
            .subscribe({ locationList ->

                if (locationList.isEmpty()) {
                    //TODO: IMPLEMENTAR LISTA VACIA
                    return@subscribe
                }

                mLocationList = locationList

                getMvpView()?.stateData()

            }, {
                Timber.e(it)

                val error = it as HttpException
                error.response().errorBody()?.let { errorResponse ->
                    val serviceError = gson.fromJson(errorResponse.string().toString(), ErrorResponse::class.java)

                    when(ServiceError.getServiceError(serviceError.error)) {
                        ServiceError.TOKEN_EXPIRED -> {
                            Timber.e("ServiceError: TOKEN_EXPIRED")
                            // TODO
                            // presenter.navigateToLogin()
                        }
                        ServiceError.UNAUTHORIZED -> {
                            Timber.e("ServiceError: UNAUTHORIZED")
                            // TODO
                            // presenter.navigateToLogin()
                        }
                        ServiceError.UNKNOWN -> {
                            Timber.e("ServiceError: UNKNOWN")
                            // TODO
                        }
                    }
                }

                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }*/

}