package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.search.ISearchPresenter
import com.zigerianos.jourtrip.presentation.scenes.search.SearchPresenter
import org.koin.dsl.module.module

val fragmentSearchModule = module {

    factory<ISearchPresenter> {
        SearchPresenter(

        )
    }
}
