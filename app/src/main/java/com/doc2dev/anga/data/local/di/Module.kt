package com.doc2dev.anga.data.local.di

import com.doc2dev.anga.data.local.AngaDb
import com.doc2dev.anga.data.local.datasource.WeatherLocalDataSource
import com.doc2dev.anga.data.local.datasource.WeatherLocalDataSourceImpl
import com.doc2dev.anga.data.local.preferences.Preferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    factory { Preferences(androidApplication()) }
    single { AngaDb.getInstance(androidApplication()) }
    factory { (get() as AngaDb).currentWeatherDao() }
    factory<WeatherLocalDataSource> { WeatherLocalDataSourceImpl(get(), get()) }
}