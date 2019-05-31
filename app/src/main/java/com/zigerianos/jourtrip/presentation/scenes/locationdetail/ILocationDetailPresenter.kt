package com.zigerianos.jourtrip.presentation.scenes.locationdetail

import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface ILocationDetailPresenter: IPresenter<ILocationDetailPresenter.ILocationDetailView> {

    fun setLocation(value: Location)

    fun getPhoto() : String
    fun getName() : String
    fun getAddress() : String
    fun getCity() : String

    fun addCommentToLocation(comment: String)

    interface ILocationDetailView: IPresenter.IView {
        fun setupToolbar()
        fun stateLoading()
        fun setupViews()
        fun stateData()
        fun stateError()

        fun loadComments(comments: List<Comment>)
        fun loadMoreComments(comments: List<Comment>)

        fun showErrorMessage()
    }
}