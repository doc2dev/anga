package com.doc2dev.anga.domain.usecase.di

import com.doc2dev.anga.domain.usecase.CurrentWeatherUseCase
import com.doc2dev.anga.domain.usecase.SaveLocationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { CurrentWeatherUseCase(get()) }
    factory { SaveLocationUseCase(get()) }
}