package com.doc2dev.anga

import android.app.Application
import timber.log.Timber

class AngaApp : Application() {
    companion object {
        lateinit var INSTANCE: AngaApp
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        KoinHelper.initDI()
        Timber.plant(Timber.DebugTree())
    }
}