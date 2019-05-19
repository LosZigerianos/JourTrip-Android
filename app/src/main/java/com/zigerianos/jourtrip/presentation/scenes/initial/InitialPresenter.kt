package com.zigerianos.jourtrip.presentation.scenes.initial

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.presentation.base.BasePresenter

class InitialPresenter(
    private val authManager: AuthManager
) : BasePresenter<IInitialPresenter.IInitialView>(), IInitialPresenter {

    override fun update() {
        super.update()

        if (authManager.isUserSignedIn()) {
            getMvpView()?.navigateToHome()
        } else {
            getMvpView()?.setupViews()
        }
    }

    override fun signupClicked() {
        getMvpView()?.navigateToSignup()
    }

    override fun existingAccountClicked() {
        getMvpView()?.navigateToLogin()
    }

}