package com.zigerianos.jourtrip.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : IPresenter.IView> : IPresenter<V> {
    private var mMvpView: V? = null

    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachMvpView(view: V) {
        mMvpView = view
    }

    override fun detachMvpView() {
        mMvpView = null
    }

    override fun getMvpView(): V? = mMvpView

    override fun initialize() {}

    override fun update() {}

    override fun pause() {}

    override fun destroy() {
        mCompositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
}
