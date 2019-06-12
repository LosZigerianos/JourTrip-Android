package com.zigerianos.jourtrip.presentation.scenes.contacts

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.usecases.GetContactsByNameUseCase
import com.zigerianos.jourtrip.domain.usecases.GetFollowersByUserUseCase
import com.zigerianos.jourtrip.domain.usecases.GetFollowingByUserUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class ContactsPresenter(
    private val authManager: AuthManager,
    private val getFollowingByUserUseCase: GetFollowingByUserUseCase,
    private val getFollowersByUserUseCase: GetFollowersByUserUseCase,
    private val getContactsByNameUseCase: GetContactsByNameUseCase
) : BasePresenter<IContactsPresenter.IContacts>(), IContactsPresenter {

    private var mUserList: MutableList<User> = mutableListOf()
    private var mTotalCount: Int = 0
    private val PAGINATION_REQUEST: Int = 12

    private var mUserId: String = ""
    private var mFollowing = false
    private var mFollowers = false

    override fun update() {
        super.update()

        getMvpView()?.clearItems()
        mUserList.clear()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        if (mFollowing) requestFollowing()
        else if (mFollowers) requestFollowers()
        else getMvpView()?.stateData()
    }

    override fun setUserId(value: String?) {
        value?.let { userId ->
            mUserId = userId
            return
        }

        authManager.getUserId()?.let {
            mUserId = it
            return
        }
    }

    override fun setFollowing(value: Boolean) {
        mFollowing = value
    }

    override fun setFollowers(value: Boolean) {
        mFollowers = value
    }

    override fun userClicked(user: User) {
        authManager.getUserId()?.let { userId ->
            if (userId == user.id)
                getMvpView()?.navigateToUserProfile(true, user)
            else
                getMvpView()?.navigateToUserProfile(false, user)
        }
    }

    override fun searchContactByName(name: String) {
        getMvpView()?.clearItems()
        mUserList.clear()

        requestContactsByName(name)
    }

    override fun loadMoreData() {
        if (mFollowing) {
            requestFollowing()
        } else if (mFollowers) {
            requestFollowers()
        }
    }

    override fun reloadDataClicked() {
        getMvpView()?.stateLoading()

        if (mFollowing) {
            requestFollowing()
        } else if (mFollowers) {
            requestFollowers()
        }
    }

    private fun requestFollowers() {
        val params = GetFollowersByUserUseCase.Params(mUserId, skip = mUserList.count(), limit = PAGINATION_REQUEST)

        val disposable = getFollowersByUserUseCase.observable(params)
            .subscribe({ response ->

                if (response.data.isEmpty()) {
                    getMvpView()?.loadUsers(emptyList())
                    getMvpView()?.stateData()
                    return@subscribe
                }

                mTotalCount = response.count

                mUserList.addAll(response.data.toMutableList())
                getMvpView()?.loadUsers(response.data, forMorePages = mUserList.count() < mTotalCount)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
                return@subscribe
            })

        addDisposable(disposable)
    }

    private fun requestFollowing() {
        val params = GetFollowingByUserUseCase.Params(mUserId, skip = mUserList.count(), limit = PAGINATION_REQUEST)

        val disposable = getFollowingByUserUseCase.observable(params)
            .subscribe({ response ->

                if (response.data.isEmpty()) {
                    getMvpView()?.loadUsers(emptyList())
                    getMvpView()?.stateData()
                    return@subscribe
                }

                mTotalCount = response.count

                mUserList.addAll(response.data.toMutableList())
                getMvpView()?.loadUsers(response.data, forMorePages = mUserList.count() < mTotalCount)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
                return@subscribe
            })

        addDisposable(disposable)
    }

    private fun requestContactsByName(name: String) {
        val params = GetContactsByNameUseCase.Params(name)

        val disposable = getContactsByNameUseCase.observable(params)
            .subscribe({ response ->

                if (response.data.isEmpty()) {
                    getMvpView()?.loadUsers(emptyList())
                    getMvpView()?.stateData()
                    return@subscribe
                }

                mTotalCount = response.count

                mUserList.addAll(response.data.toMutableList())
                getMvpView()?.loadUsers(response.data, forMorePages = mUserList.count() < mTotalCount)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
                return@subscribe
            })

        addDisposable(disposable)
    }

}