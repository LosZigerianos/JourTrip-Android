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

    private var mCommentList: MutableList<Comment> = mutableListOf()
    private var mTotalCount: Int = 0
    private val PAGINATION_REQUEST: Int = 2

    private var mUserId: String = ""
    private var mIsPersonal: Boolean = false
    private var mFollowingUser: Boolean = false

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        requestProfile(mUserId)
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
                    // TODO: Check response: user is User and location is Location and the are not String.

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
        mCommentList.clear()

        requestProfile(mUserId)
    }

    override fun loadMoreData() {
        requestMoreComments()
    }

    private fun requestProfile(userId: String) {
        val params = GetUserProfileUseCase.Params(userId = userId, skip = mCommentList.count(), limit = PAGINATION_REQUEST)

        val disposable = getUserProfileUseCase.observable(params)
            .subscribe({ profile ->

                profile.comments?.let { comments ->
                    if (comments.isEmpty()) {
                        getMvpView()?.loadComments(emptyList())
                    } else {
                        profile.commentsCount?.let { totalCount ->
                            mTotalCount = totalCount

                            mCommentList.addAll(comments.toMutableList())
                            getMvpView()?.loadComments(comments, forMorePages = mCommentList.count() < mTotalCount)
                        }
                    }
                }

                getMvpView()?.loadUser(profile)
                getMvpView()?.stateData()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

    private fun requestMoreComments() {
        val params = GetCommentsByUserUseCase.Params(userId = mUserId, skip = mCommentList.count(), limit = PAGINATION_REQUEST)

        val disposable = getCommentsByUserUseCase.observable(params)
            .subscribe({ comments ->

                if (comments.isEmpty()) {
                    getMvpView()?.loadComments(emptyList())
                    return@subscribe
                }

                mCommentList.addAll(comments.toMutableList())
                getMvpView()?.loadComments(comments, forMorePages = mCommentList.count() < mTotalCount)
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