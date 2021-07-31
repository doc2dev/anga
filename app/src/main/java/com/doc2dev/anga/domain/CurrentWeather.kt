package com.doc2dev.anga.domain

import java.time.LocalDateTime

abstract class CurrentWeather {
    abstract val weatherSummary: String
    abstract val weatherDescription: String
    abstract val currentTemperature: Double
    abstract val minTemperature: Double
    abstract val maxTemperature: Double
    abstract val placeName: String
    abstract val country: String
    abstract val created: LocalDateTime
}