package com.doc2dev.anga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doc2dev.anga.data.local.models.CurrentWeatherModel
import com.doc2dev.anga.data.local.models.ForecastModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(weather: CurrentWeatherModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecast: List<ForecastModel>)

    @Query("SELECT * FROM current_weather ORDER BY created DESC LIMIT 1")
    fun getLatestWeather(): Flow<CurrentWeatherModel>

    @Query("SELECT * FROM forecast ORDER BY created DESC LIMIT 5")
    fun getFiveDayForecast(): Flow<List<ForecastModel>>
}