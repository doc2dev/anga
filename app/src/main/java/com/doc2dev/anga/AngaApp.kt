package com.doc2dev.anga

import android.app.Application
import android.os.Build
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
        if ("robolectric" == Build.FINGERPRINT) {
            return
        }
        KoinHelper.initDI()
        Timber.plant(Timber.DebugTree())
    }
}