package com.zigerianos.jourtrip.di.core

import com.zigerianos.jourtrip.BuildConfig
import com.zigerianos.jourtrip.di.ModulesNames
import com.zigerianos.jourtrip.utils.HttpDefaultHeadersInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { HttpDefaultHeadersInterceptor(System.getProperty("http.agent")) }

    single {
        val interceptor = HttpLoggingInterceptor { message -> Timber.tag("HttpRequest").i(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    single(name = ModulesNames.CACHE_DEFAULT) {
        Cache(
            File(androidContext().cacheDir, "okhttp_cache"),
            10 * 1000 * 1000
        )
    }

    //region Picasso
    single(name = ModulesNames.CACHE_PICASSO) {
        Cache(
            File(androidContext().cacheDir, "picasso_cache"),
            Int.MAX_VALUE.toLong()
        )
    }

    /*single(name = ModulesNames.OKHTTP_PICASSO) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .cache(get(name = ModulesNames.CACHE_PICASSO))
            .retryOnConnectionFailure(true)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }*/

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<HttpDefaultHeadersInterceptor>())
            .cache(get(name = ModulesNames.CACHE_DEFAULT))
            .retryOnConnectionFailure(true)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(
                if (BuildConfig.API_VERSION > 0) {
                    "${BuildConfig.API_SERVER_URL}/apiv${BuildConfig.API_VERSION}/"
                } else {
                    "${BuildConfig.API_SERVER_URL}/"
                }
            )
            .client( get() )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create( get() ))
            .build()
    }
}
