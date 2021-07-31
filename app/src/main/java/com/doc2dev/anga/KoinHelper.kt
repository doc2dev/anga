package com.doc2dev.anga

import com.doc2dev.anga.data.local.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

object KoinHelper {
    fun initDI() {
        val modules = listOf(
            dbModule
        )
        startKoin {
            EmptyLogger()
            androidContext(AngaApp.INSTANCE)
            modules(modules)
        }
    }
}