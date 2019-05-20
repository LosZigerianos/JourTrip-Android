package com.zigerianos.jourtrip.presentation.scenes.profile

import com.zigerianos.jourtrip.presentation.base.BasePresenter

class ProfilePresenter(

) :  BasePresenter<IProfilePresenter.IProfileView>(), IProfilePresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateData()
    }

}