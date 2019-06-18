package com.zigerianos.jourtrip.presentation.scenes.userdata

import android.content.Context
import android.graphics.Bitmap
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IUserDataPresenter: IPresenter<IUserDataPresenter.IUserDataView> {

    fun uploadImage(context: Context, bitmap: Bitmap)
    fun updateDataClicked(fullname: String, username: String, email: String)
    fun updatePasswordClicked(oldPassword: String, newPassword: String)
    fun logoutClicked()
    fun reloadDataClicked()

    interface IUserDataView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()

        fun loadUser(user: User)

        fun showSuccessMessage()
        fun showErrorMessage()

        fun navigateToInit()
    }
}