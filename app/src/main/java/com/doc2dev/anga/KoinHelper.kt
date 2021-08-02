package com.doc2dev.anga

import com.doc2dev.anga.data.local.di.localModule
import com.doc2dev.anga.data.remote.di.networkModule
import com.doc2dev.anga.domain.repository.di.weatherRepoModule
import com.doc2dev.anga.domain.usecase.di.useCaseModule
import com.doc2dev.anga.ui.viewmodel.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

object KoinHelper {
    fun initDI() {
        val modules = listOf(
            networkModule,
            localModule,
            weatherRepoModule,
            useCaseModule,
            viewModelModule
        )
        startKoin {
            EmptyLogger()
            androidContext(AngaApp.INSTANCE)
            modules(modules)
        }
    }
}