package com.zigerianos.jourtrip.presentation.base

interface IPresenter<V : IPresenter.IView> {
    fun attachMvpView(view: V)

    fun detachMvpView()

    fun getMvpView(): V?

    fun initialize()

    fun update()

    fun pause()

    fun destroy()

    interface IView
}
