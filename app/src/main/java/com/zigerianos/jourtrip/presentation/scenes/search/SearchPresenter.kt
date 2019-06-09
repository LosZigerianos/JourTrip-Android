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

    private var mLocationList: MutableList<Location> = mutableListOf()
    private var mTotalCount: Int = 0
    private val PAGINATION_REQUEST: Int = 10

    private var mLastLatitude = ""
    private var mLastLongitude = ""

    private var mSearchClicked = false
    private var mLastLocation = ""

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        //getMvpView()?.stateLoading()
        getMvpView()?.setupViews()
    }

    override fun localizedUser(latitude: String, longitude: String) {
        mLastLatitude = latitude
        mLastLongitude = longitude

        requestLocationByCoordinates()
    }

    override fun searchLocationByNameClicked(name: String) {
        //getMvpView()?.stateLoading()

        if (name.isNotEmpty()) {
            mLocationList.clear()
            mSearchClicked = true
            mLastLocation = name

            requestLocationByName()
        }
    }

    override fun locationClicked(location: Location) {
        getMvpView()?.navigateToLocationDetail(location)
    }

    override fun loadMoreData() {
        if (mSearchClicked) {
            requestLocationByName()
        } else {
            requestLocationByCoordinates()
        }
    }

    private fun requestLocationByCoordinates() {
        val params = GetLocationsNearUseCase.Params(mLastLatitude, mLastLongitude, skip = mLocationList.count(), limit = PAGINATION_REQUEST)

        val disposable = getLocationsNearUseCase.observable(params)
            .subscribe({ response ->
                if (response.data.isEmpty()) {
                    getMvpView()?.loadLocations(emptyList())
                    getMvpView()?.stateData()
                    return@subscribe
                }

                mTotalCount = response.count

                mLocationList.addAll(response.data.toMutableList())
                getMvpView()?.loadLocations(response.data, forMorePages = mLocationList.count() < mTotalCount)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

    private fun requestLocationByName() {
        val params = GetLocationByNameUseCase.Params(mLastLocation, skip = mLocationList.count(), limit = PAGINATION_REQUEST)

        val disposable = getLocationByNameUseCase.observable(params)
            .subscribe({ response ->
                if (response.data.isEmpty()) {
                    getMvpView()?.loadLocations(emptyList())
                    getMvpView()?.showMessageEmpty()
                    getMvpView()?.stateData()
                    return@subscribe
                }

                mTotalCount = response.count

                mLocationList.addAll(response.data.toMutableList())
                getMvpView()?.loadLocations(response.data, forMorePages = mLocationList.count() < mTotalCount)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }
}