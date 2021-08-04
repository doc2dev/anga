package com.doc2dev.anga.domain.repository.di

import com.doc2dev.anga.data.repository.WeatherRepositoryImpl
import com.doc2dev.anga.domain.repository.WeatherRepository
import org.koin.dsl.module

val weatherRepoModule = module {
    factory<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}