package com.zigerianos.jourtrip.presentation.scenes.profile

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.domain.usecases.GetUserProfileUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class ProfilePresenter(
    private val authManager: AuthManager,
    private val getUserProfileUseCase: GetUserProfileUseCase
) :  BasePresenter<IProfilePresenter.IProfileView>(), IProfilePresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        val params = GetUserProfileUseCase.Params(authManager.getUserId() ?: "")

        val disposable = getUserProfileUseCase.observable(params)
            .subscribe({ profile ->
                getMvpView()?.loadUser(profile)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

    override fun settingsClicked() {
        getMvpView()?.navigateToUserData()
    }
}