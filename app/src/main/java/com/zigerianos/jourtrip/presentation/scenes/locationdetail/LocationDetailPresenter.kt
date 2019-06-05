package com.zigerianos.jourtrip.presentation.scenes.locationdetail

import com.zigerianos.jourtrip.data.entities.CommentRequest
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.usecases.GetCommentsByLocationUseCase
import com.zigerianos.jourtrip.domain.usecases.PostAddCommentToLocationUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class LocationDetailPresenter(
    private val getCommentsByLocationUseCase: GetCommentsByLocationUseCase,
    private val postAddCommentToLocationUseCase: PostAddCommentToLocationUseCase
) : BasePresenter<ILocationDetailPresenter.ILocationDetailView>(), ILocationDetailPresenter {

    private lateinit var mLocation: Location

    override fun setLocation(value: Location) {
        mLocation = value
    }

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.stateLoading()
        getMvpView()?.setupViews()

        requestLocationData()
    }

    override fun getPhoto(): String = mLocation.photos?.first() ?: ""

    override fun getName(): String = mLocation.name ?: ""

    override fun getAddress(): String = mLocation.formattedAddress ?: ""

    override fun getCity(): String = mLocation.city ?: ""

    override fun userClicked(user: User) {
        getMvpView()?.navigateToUserProfile(user)
    }

    private fun requestLocationData() {
        mLocation.id?.let { locationId ->
            val params = GetCommentsByLocationUseCase.Params(locationId)

            val disposable = getCommentsByLocationUseCase.observable(params)
                .subscribe({ comments ->
                    getMvpView()?.loadComments(comments)
                    getMvpView()?.stateData()
                }, {
                    Timber.e(it)
                    getMvpView()?.stateError()
                })

            addDisposable(disposable)
        }
    }

    override fun addCommentToLocation(comment: String) {
        getMvpView()?.stateLoading()

        mLocation.id?.let { locationId ->
            val commentRequest = CommentRequest(locationId, comment)
            val params = PostAddCommentToLocationUseCase.Params(commentRequest)

            val disposable = postAddCommentToLocationUseCase.observable(params)
                .subscribe({ response ->
                    requestLocationData()
                }, {
                    Timber.e(it)
                    getMvpView()?.stateData()
                    getMvpView()?.showErrorMessage()
                })

            addDisposable(disposable)
        }
    }

}