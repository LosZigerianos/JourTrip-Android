package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.di.ModulesNames
import com.zigerianos.jourtrip.presentation.scenes.home.HomePresenter
import com.zigerianos.jourtrip.presentation.scenes.home.IHomePresenter
import com.zigerianos.jourtrip.utils.CommentAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val fragmentHomeModule = module {

    factory(name = ModulesNames.ADAPTER_TIMELINE) { CommentAdapter( androidContext(), get(), true ) }

    factory<IHomePresenter> {
        HomePresenter(
            get(),
            get(),
            get()
        )
    }
}
