package com.zigerianos.jourtrip.presentation.scenes.userdata


import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
import com.zigerianos.jourtrip.R
import com.zigerianos.jourtrip.data.constants.RegexValidators
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_data.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject


class UserDataFragment : BaseFragment<IUserDataPresenter.IUserDataView, IUserDataPresenter>(), IUserDataPresenter.IUserDataView  {

    private val mainPresenter by inject<IUserDataPresenter>()

    private val picasso by inject<Picasso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun setupToolbar() {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun setupViews() {
        // TODO: VERIFICAR TITULO FINAL
        toolbar.toolbarTitle.text = "User data"

        activity?.bottomNavigationView?.visibility = View.GONE

        buttonUpdateData.setOnClickListener {
            if (checkPersonalDataFields()) {
                presenter.updateDataClicked(
                    fullname = editTextFullname.text.toString(),
                    username = editTextUsername.text.toString(),
                    email = editTextEmail.text.toString()
                )
            }
        }

        buttonUpdatePassword.setOnClickListener {
            if (checkPasswordFields()) {
                presenter.updatePasswordClicked(
                    oldPassword = editTextOldPassword.text.toString(),
                    newPassword = editTextNewPassword.text.toString()
                )
            }
        }

        buttonLogout.setOnClickListener { presenter.logoutClicked() }
    }

    override fun loadUser(user: User) {
        editTextFullname.text = Editable.Factory.getInstance().newEditable(user.fullname)
        editTextUsername.text = Editable.Factory.getInstance().newEditable(user.username)
        editTextEmail.text = Editable.Factory.getInstance().newEditable(user.email)

        picasso
            .load(user.photo)
            .placeholder(R.drawable.ic_profile_placeholder)
            .error(R.drawable.ic_profile_placeholder)
            .into(imageViewUser)
    }

    override fun stateLoading() {
        groupUserData.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun stateData() {
        groupUserData.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showSuccessMessage() {
        toast(getString(R.string.data_updated_correctly))
    }

    override fun showErrorMessage() {
        toast(R.string.error_request_message)
    }

    override fun navigateToInit() {
        NavHostFragment.findNavController(this).navigate(UserDataFragmentDirections.actionGoToInitialFragment())
    }

    private fun checkPersonalDataFields() : Boolean {
        var areFilledFields = true

        if (editTextFullname.text.toString().isEmpty()) {
            textInputLayoutFullname.isErrorEnabled = true
            textInputLayoutFullname.error = getString(R.string.required_field)
            areFilledFields = false
        } else {
            textInputLayoutFullname.isErrorEnabled = false
            textInputLayoutFullname.error = ""
        }

        if (editTextUsername.text.toString().isEmpty()) {
            textInputLayoutUsername.isErrorEnabled = true
            textInputLayoutUsername.error = getString(R.string.required_field)
            areFilledFields = false
        } else {
            textInputLayoutUsername.isErrorEnabled = false
            textInputLayoutUsername.error = ""
        }

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

        return areFilledFields
    }

    private fun checkPasswordFields() : Boolean {
        var areFilledFields = true

        if (editTextOldPassword.text.toString().isEmpty()) {
            textInputLayoutOldPassword.isErrorEnabled = true
            textInputLayoutOldPassword.error = getString(R.string.required_field)
            areFilledFields = false
        } else {
            textInputLayoutOldPassword.isErrorEnabled = false
            textInputLayoutOldPassword.error = ""
        }

        if (editTextNewPassword.text.toString().isEmpty()) {
            textInputLayoutNewPassword.isErrorEnabled = true
            textInputLayoutNewPassword.error = getString(R.string.required_field)
            areFilledFields = false
        } else {
            textInputLayoutNewPassword.isErrorEnabled = false
            textInputLayoutNewPassword.error = ""
        }

        return areFilledFields
    }

    override fun getLayoutResource(): Int = R.layout.fragment_user_data
}
