package com.doc2dev.anga.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.doc2dev.anga.domain.models.Forecast
import java.time.LocalDateTime

@Entity(tableName = "forecast")
data class ForecastModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "weather_summary")
    override val weatherSummary: String,
    @ColumnInfo(name = "day_of_week")
    override val dayOfWeek: String,
    override val temperature: Double,
    override val created: LocalDateTime
): Forecast()