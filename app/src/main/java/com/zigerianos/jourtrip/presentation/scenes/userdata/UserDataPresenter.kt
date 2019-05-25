package com.zigerianos.jourtrip.presentation.scenes.userdata

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.presentation.base.BasePresenter

class UserDataPresenter(
    private val authManager: AuthManager
): BasePresenter<IUserDataPresenter.IUserDataView>(), IUserDataPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateData()
    }

}