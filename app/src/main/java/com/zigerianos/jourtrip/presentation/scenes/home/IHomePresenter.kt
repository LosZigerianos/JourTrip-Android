package com.zigerianos.jourtrip.presentation.scenes.home

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IHomePresenter : IPresenter<IHomePresenter.IHomeView> {



    interface IHomeView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()
    }
}