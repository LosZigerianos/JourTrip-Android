package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.locationdetail.ILocationDetailPresenter
import com.zigerianos.jourtrip.presentation.scenes.locationdetail.LocationDetailPresenter
import org.koin.dsl.module.module

val fragmentLocationDetailModule = module {

    factory<ILocationDetailPresenter> {
        LocationDetailPresenter(
            get(),
            get()
        )
    }
}
