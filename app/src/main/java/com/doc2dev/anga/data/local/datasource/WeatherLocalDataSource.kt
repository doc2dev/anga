package com.doc2dev.anga.data.local.datasource

import com.doc2dev.anga.data.local.dao.WeatherDao
import com.doc2dev.anga.data.local.models.CurrentWeatherModel
import com.doc2dev.anga.data.local.models.ForecastModel
import com.doc2dev.anga.data.local.preferences.Preferences
import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.models.Forecast
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

interface WeatherLocalDataSource {
    fun getLatestWeather(): Flow<CurrentWeather>
    fun saveCurrentWeather(currentWeather: CurrentWeather)
    fun saveLocation(latitude: Float, longitude: Float)
    fun saveForecast(forecast: List<Forecast>)
    fun getFiveDayForecast(): Flow<List<ForecastModel>>
}

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao,
    private val preferences: Preferences
) : WeatherLocalDataSource {
    override fun getLatestWeather(): Flow<CurrentWeatherModel> {
        return weatherDao.getLatestWeather()
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
            weatherDao.insertCurrentWeather(weatherModel)
        }
    }

    override fun saveLocation(latitude: Float, longitude: Float) {
        preferences.saveLatitude(latitude)
        preferences.saveLongitude(longitude)
    }

    override fun saveForecast(forecast: List<Forecast>) {
        // Exclude current day forecast, then limit to 5
        val fiveDayForecast = forecast.subList(1, 6)
        val forecastModels = mutableListOf<ForecastModel>()
        fiveDayForecast.forEach {
            with(it) {
                val model = ForecastModel(
                    null,
                    weatherSummary,
                    dayOfWeek,
                    temperature,
                    created
                )
                forecastModels.add(model)
            }
        }
        weatherDao.insertForecast(forecastModels)
    }

    override fun getFiveDayForecast(): Flow<List<ForecastModel>> {
        return weatherDao.getFiveDayForecast()
    }
}