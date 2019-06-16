package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.imageviewer.IImageViewerPresenter
import com.zigerianos.jourtrip.presentation.scenes.imageviewer.ImageViewerPresenter
import org.koin.dsl.module.module

val fragmentImageViewerModule = module {
    factory<IImageViewerPresenter> {
        ImageViewerPresenter(

        )
    }
}