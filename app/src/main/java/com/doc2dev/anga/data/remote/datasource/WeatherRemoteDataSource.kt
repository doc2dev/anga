package com.doc2dev.anga.data.remote.datasource

import com.doc2dev.anga.data.remote.WeatherApiService
import com.doc2dev.anga.data.remote.models.CurrentWeatherJson
import com.doc2dev.anga.domain.models.CurrentWeather

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(): CurrentWeather
}

class WeatherRemoteDataSourceImpl(
    private val apiService: WeatherApiService
) : WeatherRemoteDataSource {
    override suspend fun getCurrentWeather(): CurrentWeatherJson {
        return apiService.getCurrentWeather()
    }
}