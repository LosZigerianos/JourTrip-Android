package com.zigerianos.jourtrip.di.core

import com.zigerianos.jourtrip.data.ApiService
import org.koin.dsl.module.module
import retrofit2.Retrofit

val dataModule = module {
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
}
