package com.doc2dev.anga.data.local.datasource

import com.doc2dev.anga.data.local.dao.CurrentWeatherDao
import com.doc2dev.anga.data.local.models.CurrentWeatherModel
import com.doc2dev.anga.data.local.preferences.Preferences
import com.doc2dev.anga.domain.models.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    fun getLatestWeather(): Flow<CurrentWeather>
    fun saveCurrentWeather(currentWeather: CurrentWeather)
    fun saveLocation(latitude: Float, longitude: Float)
}

class WeatherLocalDataSourceImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val preferences: Preferences
) : WeatherLocalDataSource {
    override fun getLatestWeather(): Flow<CurrentWeatherModel> {
        return currentWeatherDao.getLatestWeather()
    }

    override fun saveCurrentWeather(currentWeather: CurrentWeather) {
        with(currentWeather) {
            val weatherModel = CurrentWeatherModel(
                id = null,
                weatherSummary,
                weatherDescription,
                currentTemperature,
                minTemperature,
                maxTemperature,
                placeName,
                country,
                created
            )
            currentWeatherDao.insertCurrentWeather(weatherModel)
        }
    }

    override fun saveLocation(latitude: Float, longitude: Float) {
        preferences.saveLatitude(latitude)
        preferences.saveLongitude(longitude)
    }
}