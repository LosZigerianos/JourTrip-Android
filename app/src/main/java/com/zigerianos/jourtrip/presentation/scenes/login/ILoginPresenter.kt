package com.zigerianos.jourtrip.presentation.scenes.login

import android.content.Context
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ILoginPresenter: IPresenter<ILoginPresenter.ILoginView> {

    fun loginClicked(email: String, password: String, context: Context)

    fun recoveryPasswordClicked(recoveryEmail: String)

    fun hasBiometricPermission(value: Boolean)

    interface ILoginView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateDataLogin()
        fun stateDataRecoverPassword()
        fun stateError()

        fun authenticateToUser()

        fun navigateToHome()

        //fun showInvalidCredentialsErrorMessage()
        //fun showSentEmailToRecoveryPasswordMessage()
        fun showSuccessMessage(message: String)
        fun showErrorMessage(message: String)
        fun showCredentialsErrorMessage()
        fun showAuthMessage()
    }
}