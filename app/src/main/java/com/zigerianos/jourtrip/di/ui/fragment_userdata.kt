package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.userdata.IUserDataPresenter
import com.zigerianos.jourtrip.presentation.scenes.userdata.UserDataPresenter
import org.koin.dsl.module.module

val fragmentUserDataModule = module {

    factory<IUserDataPresenter> {
        UserDataPresenter(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
