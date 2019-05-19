package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.initial.IInitialPresenter
import com.zigerianos.jourtrip.presentation.scenes.initial.InitialPresenter
import org.koin.dsl.module.module

val fragmentInitialModule = module {

    factory<IInitialPresenter> {
        InitialPresenter(
            get()
        )
    }
}