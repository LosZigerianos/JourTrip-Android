package com.zigerianos.jourtrip

import timber.log.Timber

// TODO: Este es el modo DEBUG, no RELEASE
class App : BaseApp() {
    override fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
