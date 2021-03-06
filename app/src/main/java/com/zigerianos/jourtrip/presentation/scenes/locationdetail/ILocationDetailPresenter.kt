package com.zigerianos.jourtrip.presentation.scenes.locationdetail

import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ILocationDetailPresenter : IPresenter<ILocationDetailPresenter.ILocationDetailView> {

    fun setLocation(value: Location)

    fun getPhoto(): String
    fun getName(): String
    fun getCaption(): String
    fun getCity(): String

    fun addCommentToLocation(comment: String)

    fun userClicked(user: User)
    fun reloadDataClicked()
    fun imageClicked()

    fun loadMoreData()

    interface ILocationDetailView : IPresenter.IView {
        fun setupToolbar()
        fun stateLoading()
        fun setupViews()
        fun stateData()
        fun stateError()

        fun loadComments(comments: List<Comment>, forMorePages: Boolean = false)
        fun loadComment(comment: Comment)

        fun showErrorMessage()

        fun navigateToUserProfile(main: Boolean, user: User)
        fun navigateToImageViewer(images: List<String>)
    }
}