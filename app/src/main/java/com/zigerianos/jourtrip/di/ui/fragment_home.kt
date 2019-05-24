package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.home.DeadlineAdapter
import com.zigerianos.jourtrip.presentation.scenes.home.HomePresenter
import com.zigerianos.jourtrip.presentation.scenes.home.IHomePresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val fragmentHomeModule = module {

    factory { DeadlineAdapter( androidContext() ) }

    factory<IHomePresenter> {
        HomePresenter(
            get(),
            get()
        )
    }
}
