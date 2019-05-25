package com.zigerianos.jourtrip.presentation.scenes.userdata

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IUserDataPresenter: IPresenter<IUserDataPresenter.IUserDataView> {


    interface IUserDataView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
    }
}