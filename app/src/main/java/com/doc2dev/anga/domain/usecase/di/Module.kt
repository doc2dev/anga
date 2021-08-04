package com.doc2dev.anga.domain.usecase.di

import com.doc2dev.anga.domain.usecase.GetWeatherUseCase
import com.doc2dev.anga.domain.usecase.SaveLocationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetWeatherUseCase(get()) }
    factory { SaveLocationUseCase(get()) }
}