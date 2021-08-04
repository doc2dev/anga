package com.doc2dev.anga.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherJson(
    @SerializedName("main")
    @Expose
    val weatherSummary: String,
    @SerializedName("description")
    @Expose
    val weatherDescription: String
)