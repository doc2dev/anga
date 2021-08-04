package com.doc2dev.anga.data.remote

import com.doc2dev.anga.data.remote.models.CurrentWeatherJson
import com.doc2dev.anga.data.remote.models.ForecastWrapper
import retrofit2.http.GET

interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(): CurrentWeatherJson

    @GET("onecall")
    suspend fun getForecast(): ForecastWrapper
}