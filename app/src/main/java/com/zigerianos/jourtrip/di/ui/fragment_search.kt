package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.search.ISearchPresenter
import com.zigerianos.jourtrip.presentation.scenes.search.SearchPresenter
import com.zigerianos.jourtrip.utils.NearbyAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val fragmentSearchModule = module {

    factory { NearbyAdapter( androidContext(), get() ) }

    factory<ISearchPresenter> {
        SearchPresenter(
            get(),
            get()
        )
    }
}
