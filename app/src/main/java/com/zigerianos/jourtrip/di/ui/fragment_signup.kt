package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.signup.ISignupPresenter
import com.zigerianos.jourtrip.presentation.scenes.signup.SignupPresenter
import org.koin.dsl.module.module

val fragmentSignupModule = module {

    factory<ISignupPresenter> {
        SignupPresenter(
            get()
        )
    }
}
