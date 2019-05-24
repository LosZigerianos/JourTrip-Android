package com.zigerianos.jourtrip.presentation.scenes.search

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ISearchPresenter: IPresenter<ISearchPresenter.ISearchView> {

    interface ISearchView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()
    }
}