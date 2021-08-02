package com.doc2dev.anga.domain.usecase

import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class CurrentWeatherUseCase(private val weatherRepository: WeatherRepository) {
    fun getLatestWeather(): Flow<CurrentWeather> {
        return weatherRepository.getLatestWeather()
    }
    suspend fun refreshCurrentWeather() {
        weatherRepository.refreshCurrentWeather()
    }
}