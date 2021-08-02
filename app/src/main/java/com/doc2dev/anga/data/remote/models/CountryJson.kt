package com.doc2dev.anga.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryJson(
    @SerializedName("country")
    @Expose
    val country: String
)