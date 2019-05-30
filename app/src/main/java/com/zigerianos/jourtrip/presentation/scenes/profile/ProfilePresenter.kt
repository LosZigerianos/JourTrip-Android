package com.zigerianos.jourtrip.presentation.scenes.profile

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.domain.usecases.GetCommentsByUserUseCase
import com.zigerianos.jourtrip.domain.usecases.GetUserProfileUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class ProfilePresenter(
    private val authManager: AuthManager,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCommentsByUserUseCase: GetCommentsByUserUseCase
) :  BasePresenter<IProfilePresenter.IProfileView>(), IProfilePresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        val params = GetUserProfileUseCase.Params(userId = authManager.getUserId() ?: "", skip = 3)

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