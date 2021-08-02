package com.doc2dev.anga.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TemperatureJson(
    @SerializedName("temp_min")
    @Expose
    val minTemperature: Double,
    @SerializedName("temp_max")
    @Expose
    val maxTemperature: Double,
    @SerializedName("temp")
    @Expose
    val temperature: Double
)