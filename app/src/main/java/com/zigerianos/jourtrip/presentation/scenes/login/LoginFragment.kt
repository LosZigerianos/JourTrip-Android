package com.zigerianos.jourtrip.presentation.scenes.login

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.cespes.biometricauth.BiometricCallback
import com.cespes.biometricauth.BiometricManager
import com.cespes.biometricauth.BiometricUtils

import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.constants.RegexValidators
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<ILoginPresenter.ILoginView, ILoginPresenter>(), ILoginPresenter.ILoginView {

    private val mainPresenter by inject<ILoginPresenter>()

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
        toolbar.toolbarTitle.text = getString(R.string.welcome_back)

        activity?.bottomNavigationView?.visibility = View.GONE

        buttonLogin.setOnClickListener {
            if (checkLoginFields()) {
                presenter.loginClicked(
                    editTextEmail.text.toString().toLowerCase(),
                    editTextPassword.text.toString(),
                    context!!
                )
            }
        }

        textViewForgottenPassword.setOnClickListener {
            stateDataRecoverPassword()
        }

        buttonBackToLogin.setOnClickListener {
            stateDataLogin()
        }

        buttonSendRecoveryEmail.setOnClickListener {
            if (checkRecoverPasswordFields()) {
                presenter.recoveryPasswordClicked(editTextSendEmail.text.toString().toLowerCase())
            }
        }

        //editTextEmail.text = Editable.Factory.getInstance().newEditable("invitado@example.com")
        //editTextPassword.text = Editable.Factory.getInstance().newEditable("123123")
    }

    override fun stateLoading() {
        groupLogin.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateDataLogin() {
        textInputLayoutSendEmail.isErrorEnabled = false
        textInputLayoutSendEmail.error = ""
        editTextSendEmail.editableText.clear()

        groupRecoverPassword.visibility = View.GONE
        groupLogin.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateDataRecoverPassword() {
        groupLogin.visibility = View.GONE
        groupRecoverPassword.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun stateError() {
        groupRecoverPassword.visibility = View.GONE
        groupLogin.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        toast(getString(R.string.error_request_message))
    }

    override fun navigateToHome() {
        NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.actionGoToHomeFragment())
    }

    override fun showSuccessMessage(message: String) {
        toast(message)
    }

    override fun showErrorMessage(message: String) {
        stateDataLogin()
        toast(message)
    }

    override fun showCredentialsErrorMessage() {
        stateDataLogin()
        toast(getString(R.string.incorrect_user_password))
    }

    override fun showAuthMessage() {
        MaterialDialog(context!!).show {
            title(R.string.identify_fingerprint_title)
            message(R.string.identify_fingerprint_message)
            positiveButton(R.string.accept) {
                authenticateToUser()
            }
            negativeButton(R.string.cancel) {
                presenter.hasBiometricPermission(false)
            }
        }
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
                    stateDataLogin()
                }

                override fun onBiometricAuthenticationNotSupported() {
                    println("Info -> onBiometricAuthenticationNotSupported")
                    stateDataLogin()
                }

                override fun onBiometricAuthenticationNotAvailable() {
                    println("Info -> onBiometricAuthenticationNotAvailable")
                    stateDataLogin()
                }

                override fun onBiometricAuthenticationPermissionNotGranted() {
                    println("Info -> onBiometricAuthenticationPermissionNotGranted")
                    stateDataLogin()
                }

                override fun onBiometricAuthenticationInternalError(error: String) {
                    println("Info -> onBiometricAuthenticationInternalError")
                    stateDataLogin()
                }

                override fun onAuthenticationFailed() {
                    println("Info -> onAuthenticationFailed")
                    stateDataLogin()
                }

                override fun onAuthenticationCancelled() {
                    stateDataLogin()
                }

                override fun onAuthenticationSuccessful() {
                    presenter.hasBiometricPermission(true)
                    navigateToHome()
                }

                override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                    println("Info -> onAuthenticationHelp")
                    stateDataLogin()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    println("Info -> onAuthenticationError")
                    stateDataLogin()
                }
            })
    }

    private fun checkLoginFields(): Boolean {
        var areFilledFields = true

        if (editTextEmail.text.toString().isEmpty()) {
            textInputLayoutEmail.isErrorEnabled = true
            textInputLayoutEmail.error = getString(R.string.required_field)
            areFilledFields = false
        } else if (!editTextEmail.text.toString().toLowerCase().matches(Regex(RegexValidators.EMAIL))) {
            textInputLayoutEmail.isErrorEnabled = true
            textInputLayoutEmail.error = getString(R.string.required_email)
            areFilledFields = false
        } else {
            textInputLayoutEmail.isErrorEnabled = false
            textInputLayoutEmail.error = ""
        }

        if (editTextPassword.text.toString().isEmpty()) {
            textInputLayoutPassword.isErrorEnabled = true
            textInputLayoutPassword.error = getString(R.string.required_field)
            areFilledFields = false
        } else {
            textInputLayoutPassword.isErrorEnabled = false
            textInputLayoutPassword.error = ""
        }

        return areFilledFields
    }

    private fun checkRecoverPasswordFields(): Boolean {
        var areFilledFields = true

        if (editTextSendEmail.text.toString().isEmpty()) {
            textInputLayoutSendEmail.isErrorEnabled = true
            textInputLayoutSendEmail.error = getString(R.string.required_field)
            areFilledFields = false
        } else if (!editTextSendEmail.text.toString().toLowerCase().matches(Regex(RegexValidators.EMAIL))) {
            textInputLayoutSendEmail.isErrorEnabled = true
            textInputLayoutSendEmail.error = getString(R.string.required_email)
            areFilledFields = false
        } else {
            textInputLayoutSendEmail.isErrorEnabled = false
            textInputLayoutSendEmail.error = ""
        }

        return areFilledFields
    }

    override fun getLayoutResource(): Int = R.layout.fragment_login
}
