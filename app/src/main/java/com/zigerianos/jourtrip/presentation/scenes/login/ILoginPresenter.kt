package com.zigerianos.jourtrip.presentation.scenes.login

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ILoginPresenter: IPresenter<ILoginPresenter.ILoginView> {

    fun loginClicked(email: String, password: String)

    fun recoveryPasswordClicked(recoveryEmail: String)

    interface ILoginView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateDataLogin()
        fun stateDataRecoverPassword()
        fun stateError()

        fun navigateToHome()

        //fun showInvalidCredentialsErrorMessage()
        //fun showSentEmailToRecoveryPasswordMessage()
        fun showSuccessMessage(message: String)
        fun showErrorMessage(message: String)
    }
}