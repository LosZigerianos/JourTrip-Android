package com.zigerianos.jourtrip.presentation.scenes.initial

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_initial.*
import org.koin.android.ext.android.inject


class InitialFragment : BaseFragment<IInitialPresenter.IInitialView, IInitialPresenter>(), IInitialPresenter.IInitialView {

    private val mainPresenter by inject<IInitialPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)
    }

    override fun setupViews() {
        activity?.bottomNavigationView?.visibility = View.GONE

        buttonSignup.setOnClickListener { presenter.signupClicked() }

        buttonExistingAccount.setOnClickListener { presenter.existingAccountClicked() }
    }

    override fun navigateToSignup() {
        val action = InitialFragmentDirections.actionGoToSignupFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun navigateToLogin() {
        val action = InitialFragmentDirections.actionGoToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun navigateToHome() {
        NavHostFragment.findNavController(this).navigate(InitialFragmentDirections.actionGoToHomeFragment())
    }

    override fun getLayoutResource(): Int = R.layout.fragment_initial
}
