package com.doc2dev.anga.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.doc2dev.anga.domain.models.CurrentWeather
import java.time.LocalDateTime

@Entity(tableName = "current_weather")
data class CurrentWeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "weather_summary")
    override val weatherSummary: String,
    @ColumnInfo(name = "weather_description")
    override val weatherDescription: String,
    @ColumnInfo(name = "current_temperature")
    override val currentTemperature: Double,
    @ColumnInfo(name = "min_temperature")
    override val minTemperature: Double,
    @ColumnInfo(name = "max_temperature")
    override val maxTemperature: Double,
    @ColumnInfo(name = "place_name")
    override val placeName: String,
    override val country: String,
    override val created: LocalDateTime
) : CurrentWeather()