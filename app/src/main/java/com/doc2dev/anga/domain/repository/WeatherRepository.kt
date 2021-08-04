package com.doc2dev.anga.domain.repository

import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.models.Forecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getLatestWeather(): Flow<CurrentWeather>
    fun getLatestForecast(): Flow<List<Forecast>>
    suspend fun refreshWeather()
    fun saveLocation(latitude: Float, longitude: Float)
}