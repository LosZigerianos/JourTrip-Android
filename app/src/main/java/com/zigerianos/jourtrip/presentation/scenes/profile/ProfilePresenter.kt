package com.zigerianos.jourtrip.presentation.scenes.profile

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.FollowingRequest
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.usecases.*
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class ProfilePresenter(
    private val authManager: AuthManager,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCommentsByUserUseCase: GetCommentsByUserUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val postAddFollowingUseCase: PostAddFollowingUseCase,
    private val deleteFollowingUseCase: DeleteFollowingUseCase
) : BasePresenter<IProfilePresenter.IProfileView>(), IProfilePresenter {

    private var mUserId: String = ""
    private var mIsPersonal: Boolean = false
    private var mFollowingUser: Boolean = false

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

    override fun isPersonal(): Boolean = mIsPersonal

    override fun isFollowingUser(): Boolean = mFollowingUser

    override fun settingsClicked() {
        getMvpView()?.navigateToUserData()
    }

    override fun followUserClicked() {
        // TODO: IMPLEMENTAR SEGUIR USUARIO
        if (!mIsPersonal) {
            getMvpView()?.stateLoading()

            if (mFollowingUser) {
                // unfollow
                requestUnfollowUser()
            } else {
                // follow
                requestFollowUser()
            }
        }
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

    override fun removeClicked(comment: Comment) {
        comment.id?.let { commentId ->
            getMvpView()?.stateLoading()

            val params = DeleteCommentUseCase.Params(commentId)

            val disposable = deleteCommentUseCase.observable(params)
                .subscribe({ response ->
                    if (!response.success) {
                        getMvpView()?.stateData()
                        getMvpView()?.showErrorMessage()
                        return@subscribe
                    }

                    getMvpView()?.stateData()
                    getMvpView()?.removeComment(comment)
                }, {
                    Timber.e(it)
                    getMvpView()?.stateError()
                })

            addDisposable(disposable)
        }
    }

    override fun reloadDataClicked() {
        getMvpView()?.stateLoading()

        requesteProfile(mUserId)
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

    private fun requestFollowUser() {
        authManager.getUserId()?.let { personalId ->
            val params = PostAddFollowingUseCase.Params(personalId, FollowingRequest(mUserId))

            val disposable = postAddFollowingUseCase.observable(params)
                .subscribe({ response ->

                    if (!response.success) {
                        getMvpView()?.showErrorMessage()
                        return@subscribe
                    }

                    mFollowingUser = !mFollowingUser
                    getMvpView()?.stateData()
                }, {
                    Timber.e(it)
                    getMvpView()?.showErrorMessage()
                })

            addDisposable(disposable)
        }
    }

    private fun requestUnfollowUser() {
        authManager.getUserId()?.let { personalId ->
            val params = DeleteFollowingUseCase.Params(personalId, FollowingRequest(mUserId))

            val disposable = deleteFollowingUseCase.observable(params)
                .subscribe({ response ->

                    if (!response.success) {
                        getMvpView()?.showErrorMessage()
                        return@subscribe
                    }

                    mFollowingUser = !mFollowingUser
                    getMvpView()?.stateData()
                }, {
                    Timber.e(it)
                    getMvpView()?.showErrorMessage()
                })

            addDisposable(disposable)
        }
    }
}