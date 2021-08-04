package com.doc2dev.anga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doc2dev.anga.data.local.converters.Converters
import com.doc2dev.anga.data.local.dao.WeatherDao
import com.doc2dev.anga.data.local.models.CurrentWeatherModel
import com.doc2dev.anga.data.local.models.ForecastModel

@Database(
    entities = [
        CurrentWeatherModel::class,
        ForecastModel::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    Converters::class
)
abstract class AngaDb: RoomDatabase() {
    abstract fun currentWeatherDao(): WeatherDao

    companion object {
        @Volatile
        var INSTANCE: AngaDb? = null

        fun getInstance(context: Context): AngaDb {
            if (INSTANCE == null) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AngaDb::class.java,
                    "anga-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return INSTANCE!!
        }
    }
}