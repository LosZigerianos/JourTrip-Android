package com.zigerianos.jourtrip.presentation.scenes.login

import android.os.Bundle

import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<ILoginPresenter.ILoginView, ILoginPresenter>(), ILoginPresenter.ILoginView {

    private val mainPresenter by inject<ILoginPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutResource(): Int = R.layout.fragment_login
}
