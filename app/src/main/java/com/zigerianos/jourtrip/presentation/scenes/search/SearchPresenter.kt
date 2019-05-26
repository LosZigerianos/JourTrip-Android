package com.zigerianos.jourtrip.presentation.scenes.search

import com.zigerianos.jourtrip.domain.usecases.GetLocationsNearUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class SearchPresenter(
    private val getLocationsNearUseCase: GetLocationsNearUseCase
) :  BasePresenter<ISearchPresenter.ISearchView>(), ISearchPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateData()

        requestLocationByCoordinates("41.65215386690642", "-0.8807576344025847")
    }

    private fun requestLocationByCoordinates(latitude: String, longitude: String) {
        val params = GetLocationsNearUseCase.Params(latitude, longitude)

        val disposable = getLocationsNearUseCase.observable(params)
            .subscribe({ locations ->

                Timber.d("Patata locations: $locations")

            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }
}