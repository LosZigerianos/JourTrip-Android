package com.zigerianos.jourtrip.presentation.scenes.home

import com.zigerianos.jourtrip.presentation.base.BasePresenter

class HomePresenter(

) : BasePresenter<IHomePresenter.IHomeView>(), IHomePresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateData()
    }

}