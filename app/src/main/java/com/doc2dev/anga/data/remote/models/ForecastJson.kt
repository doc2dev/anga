package com.doc2dev.anga.data.remote.models

import com.doc2dev.anga.domain.models.Forecast
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class ForecastJson(
    @SerializedName("dt")
    @Expose
    val unixTimestamp: Long,
    @SerializedName("temp")
    @Expose
    val temperatureData: ForecastTemperatureJson,
    @SerializedName("weather")
    @Expose
    val weatherData: List<WeatherJson>
) : Forecast() {
    override val weatherSummary: String
        get() = weatherData[0].weatherSummary
    override val dayOfWeek: String
        get() = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(unixTimestamp),
            ZoneId.systemDefault()
        ).dayOfWeek.name
    override val temperature: Double
        get() = temperatureData.dayTemperature
    override val created: LocalDateTime
        get() = LocalDateTime.now()
}