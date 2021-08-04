package com.doc2dev.anga.data.remote.datasource

import com.doc2dev.anga.data.remote.WeatherApiService
import com.doc2dev.anga.data.remote.models.CurrentWeatherJson
import com.doc2dev.anga.data.remote.models.ForecastWrapper
import com.doc2dev.anga.domain.models.CurrentWeather

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(): CurrentWeather
    suspend fun getForecast(): ForecastWrapper
}

class WeatherRemoteDataSourceImpl(
    private val apiService: WeatherApiService
) : WeatherRemoteDataSource {
    override suspend fun getCurrentWeather(): CurrentWeatherJson {
        return apiService.getCurrentWeather()
    }

    override suspend fun getForecast(): ForecastWrapper {
        return apiService.getForecast()
    }
}