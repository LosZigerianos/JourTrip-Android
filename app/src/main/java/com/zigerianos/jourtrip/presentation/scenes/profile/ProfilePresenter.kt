package com.zigerianos.jourtrip.presentation.scenes.profile

import com.zigerianos.jourtrip.domain.usecases.GetUserMeUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class ProfilePresenter(
    private val getUserMeUseCase: GetUserMeUseCase
) :  BasePresenter<IProfilePresenter.IProfileView>(), IProfilePresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        val disposable = getUserMeUseCase.observable()
            .subscribe({ user ->
                Timber.d("Patata => User: $user ")
                getMvpView()?.loadUser(user)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

}