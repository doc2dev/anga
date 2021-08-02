package com.doc2dev.anga.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.usecase.CurrentWeatherUseCase
import com.doc2dev.anga.domain.usecase.SaveLocationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val messageLiveData = MutableLiveData<String>()
    val weatherLiveData = MutableLiveData<CurrentWeather>()

    fun getLatestWeather() {
        viewModelScope.launch(dispatcher) {
            currentWeatherUseCase.getLatestWeather().collect {
                weatherLiveData.postValue(it)
            }
        }
    }

    fun refreshWeather() {
        viewModelScope.launch(dispatcher) {
            try {
                currentWeatherUseCase.refreshCurrentWeather()
            } catch (e: Exception) {
                Timber.e(e)
                messageLiveData.postValue("Unable to get weather. Check your connection and try again.")
            }
        }
    }

    fun saveLocation(latitude: Float, longitude: Float) {
        saveLocationUseCase.saveLocation(latitude, longitude)
    }
}