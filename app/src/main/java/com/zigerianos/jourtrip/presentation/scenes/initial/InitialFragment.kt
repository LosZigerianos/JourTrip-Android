package com.zigerianos.jourtrip.presentation.scenes.initial

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_initial.*
import org.koin.android.ext.android.inject


class InitialFragment : BaseFragment<IInitialPresenter.IInitialView, IInitialPresenter>(), IInitialPresenter.IInitialView {

    private val mainPresenter by inject<IInitialPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

    }

    override fun setupViews() {
        buttonSignup.setOnClickListener { presenter.signupClicked() }

        buttonExistingAccount.setOnClickListener { presenter.existingAccountClicked() }
    }

    override fun navigateToSignup() {
        // TODO: Implementar
    }

    override fun navigateToLogin() {
        val action = InitialFragmentDirections.actionGoToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_initial
}
