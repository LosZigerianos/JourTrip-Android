package com.zigerianos.jourtrip.presentation.scenes.login

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ILoginPresenter: IPresenter<ILoginPresenter.ILoginView> {

    fun login(email: String, password: String)

    interface ILoginView: IPresenter.IView {
        //fun setupViews()
        //fun stateLoading()
    }
}