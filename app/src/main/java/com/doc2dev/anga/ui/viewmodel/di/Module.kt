package com.doc2dev.anga.ui.viewmodel.di

import com.doc2dev.anga.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { Dispatchers.IO }
    viewModel { WeatherViewModel(get(), get(), get()) }
}