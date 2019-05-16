package com.zigerianos.jourtrip.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<V : IPresenter.IView, P : IPresenter<V>> : Fragment(), IPresenter.IView {

    //protected val TAG = javaClass.simpleName

    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    protected lateinit var presenter: P

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initialize()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachMvpView(this as V)
    }

    override fun onResume() {
        super.onResume()
        presenter.update()
    }

    override fun onPause() {
        presenter.pause()
        super.onPause()
    }

    override fun onDestroyView() {
        mCompositeDisposable.clear()
        presenter.detachMvpView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
}
