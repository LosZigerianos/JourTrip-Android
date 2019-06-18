package com.zigerianos.jourtrip

import timber.log.Timber

class App : BaseApp() {
    override fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
