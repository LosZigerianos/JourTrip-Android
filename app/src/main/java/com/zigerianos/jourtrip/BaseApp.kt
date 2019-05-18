package com.zigerianos.jourtrip

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.google.gson.Gson
import com.zigerianos.jourtrip.di.core.*
import com.zigerianos.jourtrip.di.ui.fragmentInitialModule
import com.zigerianos.jourtrip.di.ui.fragmentLoginModule
import com.zigerianos.jourtrip.di.ui.fragmentSignupModule
import com.zigerianos.jourtrip.di.ui.useCasesModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.startKoin
import org.koin.log.EmptyLogger

abstract class BaseApp : MultiDexApplication() {
    private val modules = listOf(
        serializationModule,
        managersModule,
        networkModule,
        imageModule,
        dataModule,
        useCasesModule,

        fragmentInitialModule,
        fragmentLoginModule,
        fragmentSignupModule
    )

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupKoin()
    }

    abstract fun setupTimber()

    private fun setupKoin() {
        startKoin(this, modules, logger = EmptyLogger())
    }

    fun getGson(): Gson = getKoin().get()

    companion object {
        fun get(context: Context) = context.applicationContext as App
    }
}