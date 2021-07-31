package com.doc2dev.anga

import android.app.Application
import timber.log.Timber

class AngaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}