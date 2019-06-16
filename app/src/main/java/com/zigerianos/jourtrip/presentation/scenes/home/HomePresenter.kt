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

    private var mCommentList: MutableList<Comment> = mutableListOf()
    private var mTotalCount: Int = 0
    private val PAGINATION_REQUEST: Int = 10

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

    override fun loadMoreData() {
        requestTimeLine()
    }

    override fun reloadDataClicked() {
        getMvpView()?.stateLoading()

        requestTimeLine()
    }

    private fun requestTimeLine() {
        authManager.getUserId()?.let { userId ->
            val disposable = getTimeLineUseCase.observable(
                GetTimeLineUseCase.Params(userId, skip = mCommentList.count(), limit = PAGINATION_REQUEST))
                .subscribe({ response ->

                    if (response.data.isEmpty()) {
                        getMvpView()?.loadComments(emptyList())
                        getMvpView()?.stateData()
                        return@subscribe
                    }

                    mTotalCount = response.count

                    mCommentList.addAll(response.data.toMutableList())
                    getMvpView()?.loadComments(response.data, forMorePages = mCommentList.count() < mTotalCount)
                    getMvpView()?.stateData()
                }, {
                    Timber.e(it)

                    val error = it as? HttpException
                    error?.response()?.errorBody()?.let { errorResponse ->
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
                    } ?: run {
                        getMvpView()?.stateError()
                        return@subscribe
                    }
                })

            addDisposable(disposable)
        }
    }

}