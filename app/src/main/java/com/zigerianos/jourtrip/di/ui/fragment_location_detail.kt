package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.locationdetail.ILocationDetailPresenter
import com.zigerianos.jourtrip.presentation.scenes.locationdetail.LocationDetailPresenter
import com.zigerianos.jourtrip.utils.UserAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val fragmentLocationDetailModule = module {
    factory { UserAdapter(androidContext(), get()) }

    factory<ILocationDetailPresenter> {
        LocationDetailPresenter(
            get(),
            get(),
            get()
        )
    }
}
