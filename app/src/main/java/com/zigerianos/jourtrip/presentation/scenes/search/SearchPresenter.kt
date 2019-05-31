package com.zigerianos.jourtrip.presentation.scenes.search

import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.usecases.GetLocationByNameUseCase
import com.zigerianos.jourtrip.domain.usecases.GetLocationsNearUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class SearchPresenter(
    private val getLocationsNearUseCase: GetLocationsNearUseCase,
    private val getLocationByNameUseCase: GetLocationByNameUseCase
) :  BasePresenter<ISearchPresenter.ISearchView>(), ISearchPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        //getMvpView()?.stateLoading()
        getMvpView()?.setupViews()
    }

    override fun localizedUser(latitude: String, longitude: String) {
        requestLocationByCoordinates(latitude, longitude)
    }

    override fun searchLocationByNameClicked(name: String) {
        //getMvpView()?.stateLoading()

        requestLocationByName(name)
    }

    override fun locationClicked(location: Location) {
        getMvpView()?.navigateToLocationDetail(location)
    }

    private fun requestLocationByCoordinates(latitude: String, longitude: String) {
        val params = GetLocationsNearUseCase.Params(latitude, longitude)

        val disposable = getLocationsNearUseCase.observable(params)
            .subscribe({ locations ->

                Timber.d("Patata locations: $locations")

                getMvpView()?.loadLocations(locations)
                getMvpView()?.stateData()

            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

    private fun requestLocationByName(name: String) {
        val params = GetLocationByNameUseCase.Params(name)

        val disposable = getLocationByNameUseCase.observable(params)
            .subscribe({ locations ->

                Timber.d("Patata locations: $locations")

                getMvpView()?.loadLocations(locations)
                getMvpView()?.stateData()

            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }
}