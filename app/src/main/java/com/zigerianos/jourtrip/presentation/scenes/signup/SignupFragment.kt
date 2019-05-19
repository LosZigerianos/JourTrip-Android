package com.zigerianos.jourtrip.presentation.scenes.signup


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.constants.RegexValidators
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

        buttonSignup.setOnClickListener {
            if (checkLoginFields()) {
                presenter.signupClicked(
                    fullname = editTextFullname.text.toString(),
                    username = editTextUsername.text.toString(),
                    email = editTextEmail.text.toString(),
                    password = editTextPassword.text.toString()
                )
            }
        }
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

    override fun navigateToBack() {
        NavHostFragment.findNavController(this).popBackStack()
    }

    override fun showSuccessMessage() {
        toast(getString(R.string.success_registered_message))
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    private fun checkLoginFields() : Boolean {
        var areFilledFields = true

        if (editTextFullname.text.toString().isEmpty()) {
            textInputLayoutFullname.isErrorEnabled = true
            textInputLayoutFullname.error = getString(R.string.required_field)
            areFilledFields = false
        } else {
            textInputLayoutFullname.isErrorEnabled = false
            textInputLayoutFullname.error = ""
        }

        /*if (editTextUsername.text.toString().isEmpty()) {
            textInputLayoutUsername.isErrorEnabled = true
            textInputLayoutUsername.error = getString(R.string.required_field)
            areFilledFields = false
        } else {
            textInputLayoutUsername.isErrorEnabled = false
            textInputLayoutUsername.error = ""
        }*/

        if (editTextEmail.text.toString().isEmpty()) {
            textInputLayoutEmail.isErrorEnabled = true
            textInputLayoutEmail.error = getString(R.string.required_field)
            areFilledFields = false
        } else if (!editTextEmail.text.toString().matches(Regex(RegexValidators.EMAIL))) {
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

    override fun getLayoutResource(): Int = R.layout.fragment_signup
}
