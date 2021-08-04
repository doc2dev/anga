package com.doc2dev.anga.domain.usecase

import com.doc2dev.anga.domain.repository.WeatherRepository

class SaveLocationUseCase(private val weatherRepository: WeatherRepository) {
    fun saveLocation(latitude: Float, longitude: Float) {
        weatherRepository.saveLocation(latitude, longitude)
    }
}