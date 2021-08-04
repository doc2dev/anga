package com.doc2dev.anga.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doc2dev.anga.R
import com.doc2dev.anga.databinding.ItemForecastBinding
import com.doc2dev.anga.domain.models.Forecast
import com.doc2dev.anga.ui.formatAsTemperature
import com.doc2dev.anga.ui.toTitleCase
import timber.log.Timber

class ForecastAdapter(
    private val fiveDayForecast: List<Forecast>
): RecyclerView.Adapter<ForecastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = fiveDayForecast[position]
        with (holder) {
            val weatherSummary = forecast.weatherSummary.toLowerCase()
            var iconResource = R.drawable.clear
            if (weatherSummary.contains("cloud")) {
                iconResource = R.drawable.partly_sunny
            } else if (weatherSummary.contains("rain")) {
                iconResource = R.drawable.rainy
            }
            weatherIcon.setImageResource(iconResource)
            dayTextView.text = forecast.dayOfWeek.toTitleCase()
            tempTextView.text = forecast.temperature.formatAsTemperature()
        }
    }

    override fun getItemCount(): Int = fiveDayForecast.size
}