package com.zigerianos.jourtrip.presentation.scenes.signup

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ISignupPresenter : IPresenter<ISignupPresenter.ISignupView> {

    //fun signupClicked(email: String, password: String)

    interface ISignupView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()
        /*fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()

        fun navigateToMain()
        fun navigateToBack()

        //fun showInvalidCredentialsErrorMessage()
        //fun showSentEmailToRecoveryPasswordMessage()
        fun showSuccessMessage(message: String)
        fun showErrorMessage(message: String)*/
    }
}