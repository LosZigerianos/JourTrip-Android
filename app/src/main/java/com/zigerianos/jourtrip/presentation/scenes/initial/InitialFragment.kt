package com.zigerianos.jourtrip.presentation.scenes.initial

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.cespes.biometricauth.BiometricCallback
import com.cespes.biometricauth.BiometricManager
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

    override fun authenticateToUser() {
        BiometricManager.BiometricBuilder(context!!)
            .setTitle("Biometric identification")
            .setSubtitle("")
            .setDescription("")
            .setNegativeButtonText(getString(R.string.cancel))
            .build()
            .authenticate(object : BiometricCallback {
                override fun onSdkVersionNotSupported() {
                    println("Info -> onSdkVersionNotSupported")
                    setupViews()
                }

                override fun onBiometricAuthenticationNotSupported() {
                    println("Info -> onBiometricAuthenticationNotSupported")
                    setupViews()
                }

                override fun onBiometricAuthenticationNotAvailable() {
                    println("Info -> onBiometricAuthenticationNotAvailable")
                    setupViews()
                }

                override fun onBiometricAuthenticationPermissionNotGranted() {
                    println("Info -> onBiometricAuthenticationPermissionNotGranted")
                    setupViews()
                }

                override fun onBiometricAuthenticationInternalError(error: String) {
                    println("Info -> onBiometricAuthenticationInternalError")
                    setupViews()
                }

                override fun onAuthenticationFailed() {
                    println("Info -> onAuthenticationFailed")
                    setupViews()
                }

                override fun onAuthenticationCancelled() {
                    setupViews()
                }

                override fun onAuthenticationSuccessful() {
                    navigateToHome()
                }

                override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                    println("Info -> onAuthenticationHelp")
                    setupViews()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    println("Info -> onAuthenticationError")
                    setupViews()
                }
            })
    }

    override fun getLayoutResource(): Int = R.layout.fragment_initial
}
