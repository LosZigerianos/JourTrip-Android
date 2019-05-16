package com.zigerianos.jourtrip.di.core

import com.google.gson.GsonBuilder
import org.koin.dsl.module.module

val serializationModule = module {
    single { GsonBuilder().create() }
}
