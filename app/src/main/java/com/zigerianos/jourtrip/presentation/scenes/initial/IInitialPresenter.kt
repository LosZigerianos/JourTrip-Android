package com.zigerianos.jourtrip.presentation.scenes.initial

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IInitialPresenter: IPresenter<IInitialPresenter.IInitialView> {

    fun signupClicked()
    fun existingAccountClicked()

    interface IInitialView: IPresenter.IView {
        fun setupViews()
        /*fun setupToolbar()
        fun stateLoading()
        fun stateData()
        fun stateError()*/

        fun navigateToSignup()
        fun navigateToLogin()
    }
}