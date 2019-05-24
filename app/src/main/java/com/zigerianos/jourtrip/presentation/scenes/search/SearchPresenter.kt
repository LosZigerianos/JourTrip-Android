package com.zigerianos.jourtrip.presentation.scenes.search

import com.zigerianos.jourtrip.presentation.base.BasePresenter

class SearchPresenter(

) :  BasePresenter<ISearchPresenter.ISearchView>(), ISearchPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateData()
    }

}