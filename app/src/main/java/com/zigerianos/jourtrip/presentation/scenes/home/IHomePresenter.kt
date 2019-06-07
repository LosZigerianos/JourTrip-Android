package com.zigerianos.jourtrip.presentation.scenes.home

import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IHomePresenter : IPresenter<IHomePresenter.IHomeView> {

    fun locationClicked(location: Location)
    fun reloadDataClicked()

    interface IHomeView: IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun stateLoading()
        fun stateData()
        fun stateError()

        fun loadComments(comments: List<Comment>)

        fun navigateToLocationDetail(location: Location)
        fun navigateToInit()
    }
}