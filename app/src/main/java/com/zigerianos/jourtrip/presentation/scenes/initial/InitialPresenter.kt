package com.zigerianos.jourtrip.presentation.scenes.initial

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.presentation.base.BasePresenter

class InitialPresenter(
    private val authManager: AuthManager
) : BasePresenter<IInitialPresenter.IInitialView>(), IInitialPresenter {

}