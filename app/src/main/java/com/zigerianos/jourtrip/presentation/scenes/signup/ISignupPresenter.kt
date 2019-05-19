package com.zigerianos.jourtrip.presentation.scenes.signup

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ISignupPresenter : IPresenter<ISignupPresenter.ISignupView> {

    fun signupClicked(
        fullname: String,
        username: String,
        email: String,
        password: String)

    interface ISignupView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()

        fun navigateToBack()

        fun showSuccessMessage()
        fun showMessage(message: String)
    }
}