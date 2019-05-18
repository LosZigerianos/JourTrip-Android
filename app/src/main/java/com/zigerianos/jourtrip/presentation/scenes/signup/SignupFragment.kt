package com.zigerianos.jourtrip.presentation.scenes.signup


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject


class SignupFragment : BaseFragment<ISignupPresenter.ISignupView, ISignupPresenter>(), ISignupPresenter.ISignupView {

    private val mainPresenter by inject<ISignupPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)

        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupViews() {
        toolbar.toolbarTitle.text = getString(R.string.sign_up)


    }

    override fun stateLoading() {
        scrollViewSignup.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        scrollViewSignup.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        scrollViewSignup.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        toast(getString(R.string.error_request_message))
    }

    override fun getLayoutResource(): Int = R.layout.fragment_signup
}
