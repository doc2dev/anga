package com.doc2dev.anga.data.remote.di

import com.doc2dev.anga.data.remote.WeatherApiService
import com.doc2dev.anga.data.remote.createRetrofit
import com.doc2dev.anga.data.remote.datasource.WeatherRemoteDataSource
import com.doc2dev.anga.data.remote.datasource.WeatherRemoteDataSourceImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { createRetrofit() }
    single { (get() as Retrofit).create(WeatherApiService::class.java) }
    factory<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get()) }
}