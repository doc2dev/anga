package com.doc2dev.anga.domain.models

import java.time.LocalDateTime

abstract class Forecast {
    abstract val weatherSummary: String
    abstract val dayOfWeek: String
    abstract val temperature: Double
    abstract val created: LocalDateTime
}