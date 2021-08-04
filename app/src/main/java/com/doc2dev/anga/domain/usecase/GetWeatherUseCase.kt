package com.doc2dev.anga.domain.usecase

import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.models.Forecast
import com.doc2dev.anga.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    fun getLatestWeather(): Flow<CurrentWeather> {
        return weatherRepository.getLatestWeather()
    }

    fun getLatestForecast(): Flow<List<Forecast>> {
        return weatherRepository.getLatestForecast()
    }

    suspend fun refreshWeather() {
        weatherRepository.refreshWeather()
    }
}