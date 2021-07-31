package com.doc2dev.anga.data.local.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun fromMillis(millis: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())

    @TypeConverter
    fun toMillis(localDateTime: LocalDateTime): Long {
        val offset = OffsetDateTime.now().offset
        return localDateTime.toEpochSecond(offset) * 1000L
    }
}