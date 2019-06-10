package com.zigerianos.jourtrip.presentation.scenes.contacts

import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IContactsPresenter : IPresenter<IContactsPresenter.IContacts> {

    fun setUserId(value: String?)
    fun setFollowing(value: Boolean)
    fun setFollowers(value: Boolean)

    fun userClicked(user: User)
    fun loadMoreData()
    fun reloadDataClicked()

    interface IContacts: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()

        fun clearItems()

        fun loadUsers(users: List<User>, forMorePages: Boolean = false)

        fun navigateToUserProfile(main: Boolean, user: User)
    }
}
