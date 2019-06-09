package com.zigerianos.jourtrip.presentation.scenes.imageviewer

import com.zigerianos.jourtrip.presentation.base.IPresenter

interface IImageViewerPresenter : IPresenter<IImageViewerPresenter.IImageViewerView> {

    fun setImages(value: List<String>)

    interface IImageViewerView : IPresenter.IView {
        fun setupToolbar()
        fun setupViews()
        fun loadImages(images: List<String>)
    }
}