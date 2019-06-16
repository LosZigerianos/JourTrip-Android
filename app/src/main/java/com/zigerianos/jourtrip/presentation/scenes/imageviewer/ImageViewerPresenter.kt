package com.zigerianos.jourtrip.presentation.scenes.imageviewer

import com.zigerianos.jourtrip.presentation.base.BasePresenter

class ImageViewerPresenter(

) : BasePresenter<IImageViewerPresenter.IImageViewerView>(), IImageViewerPresenter {

    private var mImagesList: List<String> = emptyList()

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.loadImages(mImagesList)
    }

    override fun setImages(value: List<String>) {
        mImagesList = value
    }

}