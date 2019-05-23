package com.zigerianos.jourtrip.presentation.scenes.home

import com.google.gson.Gson
import com.zigerianos.jourtrip.data.entities.ErrorResponse
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.ServiceError
import com.zigerianos.jourtrip.domain.usecases.GetLocationsByCityUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import retrofit2.HttpException
import timber.log.Timber

class HomePresenter(
    private val getLocationsByCityUseCase: GetLocationsByCityUseCase,
    private val gson: Gson
) : BasePresenter<IHomePresenter.IHomeView>(), IHomePresenter {

    private var mLocationList: List<Location> = emptyList()

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()

        fetchDeadline("Zaragoza")
    }

    fun fetchDeadline(city: String) {
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
                            Timber.e("ServiceError: UNAUTHORIZED")
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
    }

}