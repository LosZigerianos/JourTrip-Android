package com.zigerianos.jourtrip.presentation.scenes.initial

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_initial.*
import org.koin.android.ext.android.inject
import com.zigerianos.jourtrip.biometric.BiometricManager
import com.zigerianos.jourtrip.biometric.BiometricCallback


class InitialFragment : BaseFragment<IInitialPresenter.IInitialView, IInitialPresenter>(), IInitialPresenter.IInitialView {

    private val mainPresenter by inject<IInitialPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        activity?.bottomNavigationView?.visibility = View.GONE
    }

    override fun stateLoading() {
        groupInitial.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun setupViews() {
        groupInitial.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        buttonSignup.setOnClickListener { presenter.signupClicked() }
        buttonExistingAccount.setOnClickListener {

            BiometricManager.BiometricBuilder(context!!)
                .setTitle("Add a title")
                .setSubtitle("Add a subtitle")
                .setDescription("Add a description")
                .setNegativeButtonText("Add a cancel button")
                .build()
                .authenticate(object : BiometricCallback {
                    override fun onSdkVersionNotSupported() {
                        println("Patata -> onSdkVersionNotSupported")
                    }

                    override fun onBiometricAuthenticationNotSupported() {
                        println("Patata -> onBiometricAuthenticationNotSupported")
                    }

                    override fun onBiometricAuthenticationNotAvailable() {
                        println("Patata -> onBiometricAuthenticationNotAvailable")
                    }

                    override fun onBiometricAuthenticationPermissionNotGranted() {
                        println("Patata -> onBiometricAuthenticationPermissionNotGranted")
                    }

                    override fun onBiometricAuthenticationInternalError(error: String) {
                        println("Patata -> onBiometricAuthenticationInternalError")
                    }

                    override fun onAuthenticationFailed() {
                        println("Patata -> onAuthenticationFailed")
                    }

                    override fun onAuthenticationCancelled() {
                        println("Patata -> onAuthenticationCancelled")
                    }

                    override fun onAuthenticationSuccessful() {
                        println("Patata -> onAuthenticationSuccessful")
                    }

                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                        println("Patata -> onAuthenticationHelp")
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        println("Patata -> onAuthenticationError")
                    }

                })


            //presenter.existingAccountClicked()
        }
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
