package com.zigerianos.jourtrip.presentation.scenes.contacts

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.usecases.GetFollowersByUserUseCase
import com.zigerianos.jourtrip.domain.usecases.GetFollowingByUserUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class ContactsPresenter(
    private val authManager: AuthManager,
    private val getFollowingByUserUseCase: GetFollowingByUserUseCase,
    private val getFollowersByUserUseCase: GetFollowersByUserUseCase
) : BasePresenter<IContactsPresenter.IContacts>(), IContactsPresenter {

    private var mFollowing = false
    private var mFollowers = false

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        if (mFollowing) {
            requestFollowing()
        } else if (mFollowers) {
            requestFollowers()
        }
    }

    override fun setFollowing(value: Boolean) {
        mFollowing = value
    }

    override fun setFollowers(value: Boolean) {
        mFollowers = value
    }

    override fun userClicked(user: User) {
        getMvpView()?.navigateToUserProfile(user)
    }

    private fun requestFollowers() {
        authManager.getUserId()?.let { userId ->
            val params = GetFollowersByUserUseCase.Params(userId)

            val disposable = getFollowersByUserUseCase.observable(params)
                .subscribe({ contacts ->

                    if (contacts.isEmpty()) {
                        //TODO: IMPLEMENTAR LISTA VACIA
                        getMvpView()?.stateData()
                        return@subscribe
                    }

                    getMvpView()?.loadUsers(contacts)
                    getMvpView()?.stateData()

                }, {
                    Timber.e(it)
                    getMvpView()?.stateError()
                    return@subscribe
                })

            addDisposable(disposable)
        }
    }

    private fun requestFollowing() {
        authManager.getUserId()?.let { userId ->
            val params = GetFollowingByUserUseCase.Params(userId)

            val disposable = getFollowingByUserUseCase.observable(params)
                .subscribe({ contacts ->

                    if (contacts.isEmpty()) {
                        //TODO: IMPLEMENTAR LISTA VACIA
                        getMvpView()?.stateData()
                        return@subscribe
                    }

                    getMvpView()?.loadUsers(contacts)
                    getMvpView()?.stateData()

                }, {
                    Timber.e(it)
                    getMvpView()?.stateError()
                    return@subscribe
                })

            addDisposable(disposable)
        }
    }

}