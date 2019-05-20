package com.zigerianos.jourtrip.presentation.scenes.home

import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.usecases.GetLocationsByCityUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class HomePresenter(
    private val getLocationsByCityUseCase: GetLocationsByCityUseCase
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
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

}