package com.zigerianos.jourtrip.presentation.scenes.userdata

import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IUserDataPresenter: IPresenter<IUserDataPresenter.IUserDataView> {

    fun updateDataClicked(fullname: String, username: String, email: String)
    fun updatePasswordClicked(oldPassword: String, newPassword: String)
    fun logoutClicked()

    interface IUserDataView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()

        fun loadUser(user: User)

        fun showSuccessMessage()
        fun showErrorMessage()

        fun navigateToInit()
    }
}