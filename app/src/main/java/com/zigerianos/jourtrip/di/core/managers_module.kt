package com.zigerianos.jourtrip.di.core

import com.zigerianos.jourtrip.PrefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val managersModule = module {
    single { PrefsManager( androidContext() ) }
}
