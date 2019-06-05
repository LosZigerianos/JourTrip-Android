package com.zigerianos.jourtrip.presentation.scenes.profile

import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.UserProfile
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IProfilePresenter : IPresenter<IProfilePresenter.IProfileView> {

    fun setUserId(value: String?)
    fun isPersonal(): Boolean
    fun isFollowingUser(): Boolean

    fun settingsClicked()
    fun followUserClicked()
    fun followingClicked()
    fun followersClicked()
    fun locationClicked(location: Location)

    interface IProfileView : IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()

        fun loadUser(profile: UserProfile)

        fun showErrorMessage()

        fun followUserChanged()

        fun navigateToUserData()
        fun navigateToContacts(userId: String, myFollowings: Boolean = false, myFollowers: Boolean = false)
        fun navigateToLocationDetail(location: Location)
    }
}