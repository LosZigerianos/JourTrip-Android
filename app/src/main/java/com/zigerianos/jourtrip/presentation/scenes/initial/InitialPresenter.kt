package com.zigerianos.jourtrip.presentation.scenes.initial

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.domain.usecases.GetTokenValidationUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class InitialPresenter(
    private val authManager: AuthManager,
    private val getTokenValidationUseCase: GetTokenValidationUseCase
) : BasePresenter<IInitialPresenter.IInitialView>(), IInitialPresenter {

    override fun update() {
        super.update()

        getMvpView()?.stateLoading()

        val params = GetTokenValidationUseCase.Params(authManager.getUser()?.accessToken)

        val disposable = getTokenValidationUseCase.observable(params)
            .subscribe({ response ->

                if (!response.success) {
                    getMvpView()?.setupViews()
                    return@subscribe
                }

                getMvpView()?.navigateToHome()

            }, {
                Timber.e(it)
                getMvpView()?.setupViews()
                return@subscribe
            })

        addDisposable(disposable)
    }

    override fun signupClicked() {
        getMvpView()?.navigateToSignup()
    }

    override fun existingAccountClicked() {
        getMvpView()?.navigateToLogin()
    }

}