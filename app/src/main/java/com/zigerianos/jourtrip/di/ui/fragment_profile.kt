package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.di.ModulesNames
import com.zigerianos.jourtrip.presentation.scenes.profile.IProfilePresenter
import com.zigerianos.jourtrip.presentation.scenes.profile.ProfilePresenter
import com.zigerianos.jourtrip.utils.CommentAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val fragmentProfileModule = module {

    factory(name = ModulesNames.ADAPTER_PROFILE) { CommentAdapter( androidContext(), get(), false ) }

    factory<IProfilePresenter> {
        ProfilePresenter(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}