package com.zigerianos.jourtrip.presentation.scenes.initial


import android.os.Bundle
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import org.koin.android.ext.android.inject


class InitialFragment : BaseFragment<IInitialPresenter.IInitialView, IInitialPresenter>(), IInitialPresenter.IInitialView {

    private val mainPresenter by inject<IInitialPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

    }


    override fun getLayoutResource(): Int = R.layout.fragment_initial
}
