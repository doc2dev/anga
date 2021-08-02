package com.doc2dev.anga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doc2dev.anga.data.local.models.CurrentWeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(weather: CurrentWeatherModel)

    @Query("SELECT * FROM current_weather ORDER BY created DESC LIMIT 1")
    fun getLatestWeather(): Flow<CurrentWeatherModel>
}