package com.zigerianos.jourtrip.presentation.scenes.initial

import android.util.Log
import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.presentation.base.BasePresenter

class InitialPresenter(
    private val authManager: AuthManager
) : BasePresenter<IInitialPresenter.IInitialView>(), IInitialPresenter {

    override fun initialize() {
        super.initialize()

        if (authManager.isUserSignedIn()) {
            // TODO: IMPLEMENTAR
            Log.d("PATATA", "Navegar directamente a la DeadLine")
        }
    }

    override fun update() {
        super.update()

        getMvpView()?.setupViews()
    }

    override fun signupClicked() {
        getMvpView()?.navigateToSignup()
    }

    override fun existingAccountClicked() {
        getMvpView()?.navigateToLogin()
    }

}