package com.zigerianos.jourtrip.presentation.scenes.search

import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ISearchPresenter: IPresenter<ISearchPresenter.ISearchView> {

    fun localizedUser(latitude: String, longitude: String)
    fun searchLocationByNameClicked(name: String)

    interface ISearchView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()

        fun loadLocations(locations: List<Location>)

        // TODO
        //fun navigateToLocation()
    }
}