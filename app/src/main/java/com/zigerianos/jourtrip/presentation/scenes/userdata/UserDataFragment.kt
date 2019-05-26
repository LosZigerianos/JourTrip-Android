package com.zigerianos.jourtrip.presentation.scenes.userdata


import android.Manifest.*
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.zigerianos.jourtrip.data.constants.ImageType.CAMERA_TYPE
import com.zigerianos.jourtrip.data.constants.ImageType.GALLERY_TYPE
import com.zigerianos.jourtrip.data.constants.RegexValidators
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.BaseFragment
import com.zigerianos.jourtrip.utils.CheckPermission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_data.*
import kotlinx.android.synthetic.main.toolbar_elevated.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import com.zigerianos.jourtrip.R
import android.content.pm.PackageManager


class UserDataFragment : BaseFragment<IUserDataPresenter.IUserDataView, IUserDataPresenter>(),
    IUserDataPresenter.IUserDataView {

    private val mainPresenter by inject<IUserDataPresenter>()

    private val picasso by inject<Picasso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = mainPresenter
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GALLERY_TYPE -> {
                data?.let { dataResponse ->
                    val contentURI = dataResponse.data
                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    presenter.uploadImage(context!!, bitmap)
                }
            }

            CAMERA_TYPE -> {
                data?.extras?.let { extrasResponse ->
                    val thumbnail = extrasResponse.get("data") as Bitmap
                    presenter.uploadImage(context!!, thumbnail)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {


        when (requestCode) {
            CheckPermission.TAG_PERMISSION_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    choosePhotoFromGallery()
                } else {
                    // permission denied
                }
                return
            }

            CheckPermission.TAG_PERMISSION_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    takePhotoFromCamera()
                } else {
                    // permission denied
                }
                return
            }

            else -> { }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

        imageViewUser.setOnClickListener {
            showPictureDialog()
        }
    }

    override fun loadUser(user: User) {
        editTextFullname.text = Editable.Factory.getInstance().newEditable(user.fullname)
        editTextUsername.text = Editable.Factory.getInstance().newEditable(user.username)
        editTextEmail.text = Editable.Factory.getInstance().newEditable(user.email)

        picasso
            .load(user.photo)
            .placeholder(R.drawable.ic_profile_placeholder)
            .error(R.drawable.ic_profile_placeholder)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .networkPolicy(NetworkPolicy.NO_CACHE)
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

    private fun checkPersonalDataFields(): Boolean {
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

    private fun checkPasswordFields(): Boolean {
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

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(context!!)
        pictureDialog.setTitle(getString(R.string.select_action))

        val pictureDialogItems = arrayOf(getString(R.string.select_photo_from_gallery), getString(R.string.capture_photo_from_camera))
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> {
                    if (CheckPermission.checkPermission(activity!!, permission.READ_EXTERNAL_STORAGE))
                        choosePhotoFromGallery()
                    else
                        CheckPermission.requestPermission(
                            this,
                            CheckPermission.TAG_PERMISSION_READ_EXTERNAL_STORAGE,
                            permission.READ_EXTERNAL_STORAGE,
                            CheckPermission.TAG_MESSAGE_PERMISSION_READ_EXTERNAL_STORAGE
                        )
                }

                1 -> {
                    if (CheckPermission.checkPermission(activity!!, permission.CAMERA))
                        takePhotoFromCamera()
                    else
                        CheckPermission.requestPermission(
                            this,
                            CheckPermission.TAG_PERMISSION_CAMERA,
                            permission.CAMERA,
                            CheckPermission.TAG_MESSAGE_PERMISSION_CAMERA
                        )
                }
            }
        }

        pictureDialog.show()
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY_TYPE)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_TYPE)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_user_data
}
