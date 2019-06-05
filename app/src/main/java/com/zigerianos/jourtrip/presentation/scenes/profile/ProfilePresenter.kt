package com.zigerianos.jourtrip.presentation.scenes.profile

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.usecases.GetCommentsByUserUseCase
import com.zigerianos.jourtrip.domain.usecases.GetUserProfileUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class ProfilePresenter(
    private val authManager: AuthManager,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCommentsByUserUseCase: GetCommentsByUserUseCase
) : BasePresenter<IProfilePresenter.IProfileView>(), IProfilePresenter {

    private var mUserId: String = ""
    private var mIsPersonal: Boolean = false

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()


        requesteProfile(mUserId)
    }

    override fun setUserId(value: String?) {
        value?.let { userId ->
            mUserId = userId

            authManager.getUserId()?.let { personalUserId ->
                if (personalUserId == userId) {
                    mIsPersonal = true
                }
            }

            return
        }

        authManager.getUserId()?.let {
            mUserId = it
            mIsPersonal = true
            return
        }
    }

    override fun getIsPersonal(): Boolean = mIsPersonal

    override fun settingsClicked() {
        getMvpView()?.navigateToUserData()
    }

    override fun followUserClicked() {
        // TODO: IMPLEMENTAR SEGUIR USUARIO
    }

    override fun followingClicked() {
        getMvpView()?.navigateToContacts(userId = mUserId, myFollowings = true)
    }

    override fun followersClicked() {
        getMvpView()?.navigateToContacts(userId = mUserId, myFollowers = true)
    }

    override fun locationClicked(location: Location) {
        getMvpView()?.navigateToLocationDetail(location)
    }

    private fun requesteProfile(userId: String) {
        val params = GetUserProfileUseCase.Params(userId = userId)

        val disposable = getUserProfileUseCase.observable(params)
            .subscribe({ profile ->
                getMvpView()?.loadUser(profile)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }
}