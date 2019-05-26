package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.profile.IProfilePresenter
import com.zigerianos.jourtrip.presentation.scenes.profile.ProfilePresenter
import org.koin.dsl.module.module

val fragmentProfileModule = module {
    factory<IProfilePresenter> {
        ProfilePresenter(
            get()
        )
    }
}