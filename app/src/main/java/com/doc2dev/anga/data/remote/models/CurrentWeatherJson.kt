package com.doc2dev.anga.data.remote.models

import com.doc2dev.anga.domain.models.CurrentWeather
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CurrentWeatherJson(
    @SerializedName("weather")
    @Expose
    val weatherData: List<WeatherJson>,
    @SerializedName("main")
    @Expose
    val temperatureData: TemperatureJson,
    @SerializedName("sys")
    @Expose
    val countryData: CountryJson,
    @SerializedName("name")
    @Expose
    override val placeName: String
) : CurrentWeather() {
    override val weatherSummary: String
        get() = weatherData[0].weatherSummary
    override val weatherDescription: String
        get() = weatherData[0].weatherDescription
    override val currentTemperature: Double
        get() = temperatureData.temperature
    override val minTemperature: Double
        get() = temperatureData.minTemperature
    override val maxTemperature: Double
        get() = temperatureData.maxTemperature
    override val country: String
        get() = countryData.country
    override val created: LocalDateTime
        get() = LocalDateTime.now()
}