package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.login.ILoginPresenter
import com.zigerianos.jourtrip.presentation.scenes.login.LoginPresenter
import org.koin.dsl.module.module

val fragmentLoginModule = module {

    factory<ILoginPresenter> {
        LoginPresenter(
            get(),
            get(),
            get()
        )
    }
}
