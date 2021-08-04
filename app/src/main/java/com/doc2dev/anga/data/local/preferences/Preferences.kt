package com.doc2dev.anga.data.local.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "anga_shared_prefs",
        MODE_PRIVATE
    )

    private fun editSharedPref(action: (s: SharedPreferences.Editor) -> Unit) {
        with(sharedPreferences.edit()) {
            action(this)
            apply()
        }
    }

    fun saveLatitude(latitude: Float) {
        editSharedPref {
            it.putFloat(LATITUDE, latitude)
        }
    }

    fun saveLongitude(longitude: Float) {
        editSharedPref {
            it.putFloat(LONGITUDE, longitude)
        }
    }

    fun getLatitude(): Float {
        return sharedPreferences.getFloat(LATITUDE, 0.0f)
    }

    fun getLongitude(): Float {
        return sharedPreferences.getFloat(LONGITUDE, 0.0f)
    }

    companion object {
        private const val LATITUDE = "latitude_key"
        private const val LONGITUDE = "longitude_key"
    }
}