package com.zigerianos.jourtrip.di.core

import com.zigerianos.jourtrip.PrefsManager
import com.zigerianos.jourtrip.auth.AuthManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val managersModule = module {
    single { PrefsManager( androidContext() ) }

    single { AuthManager( get() ) }
}
