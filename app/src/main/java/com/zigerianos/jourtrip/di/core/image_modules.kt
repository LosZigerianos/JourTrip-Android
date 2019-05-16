package com.zigerianos.jourtrip.di.core

import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val imageModule = module {
    single { OkHttp3Downloader(get<OkHttpClient>()) }

    single {
        Picasso.Builder(androidContext())
            .downloader(get<OkHttp3Downloader>())
            .build()
    }
}
