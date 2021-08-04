package com.doc2dev.anga.data.repository

import com.doc2dev.anga.data.local.datasource.WeatherLocalDataSource
import com.doc2dev.anga.data.remote.datasource.WeatherRemoteDataSource
import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(
    private val localDataSource: WeatherLocalDataSource,
    private val remoteDataSource: WeatherRemoteDataSource
): WeatherRepository {
    override fun getLatestWeather(): Flow<CurrentWeather> {
        return localDataSource.getLatestWeather()
    }

    override suspend fun refreshCurrentWeather() {
        val currentWeather = remoteDataSource.getCurrentWeather()
        localDataSource.saveCurrentWeather(currentWeather)
    }

    override fun saveLocation(latitude: Float, longitude: Float) {
        localDataSource.saveLocation(latitude, longitude)
    }
}