package com.doc2dev.anga.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doc2dev.anga.domain.models.CurrentWeather
import com.doc2dev.anga.domain.models.Forecast
import com.doc2dev.anga.domain.usecase.GetWeatherUseCase
import com.doc2dev.anga.domain.usecase.SaveLocationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val messageLiveData = MutableLiveData<String>()
    val weatherLiveData = MutableLiveData<CurrentWeather>()
    val forecastLiveData = MutableLiveData<List<Forecast>>()

    fun getLatestWeather() {
        viewModelScope.launch(dispatcher) {
            getWeatherUseCase.getLatestWeather().collect {
                weatherLiveData.postValue(it)
            }
        }
        viewModelScope.launch(dispatcher) {
            getWeatherUseCase.getLatestForecast().collect {
                forecastLiveData.postValue(it)
            }
        }
    }

    fun refreshWeather() {
        viewModelScope.launch(dispatcher) {
            try {
                getWeatherUseCase.refreshWeather()
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