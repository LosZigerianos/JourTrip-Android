package com.zigerianos.jourtrip.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zigerianos.jourtrip.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.IOException

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

        if (!isOnline()) {
            view?.let { viewFrag ->
                Snackbar.make(viewFrag, getString(R.string.needs_connection_to_internet), Snackbar.LENGTH_LONG).show()
            }
        }

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

    protected fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }
}
