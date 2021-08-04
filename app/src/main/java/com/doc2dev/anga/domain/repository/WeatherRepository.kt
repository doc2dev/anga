package com.doc2dev.anga.domain.repository

import com.doc2dev.anga.domain.models.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getLatestWeather(): Flow<CurrentWeather>
    suspend fun refreshCurrentWeather()
    fun saveLocation(latitude: Float, longitude: Float)
}