package com.doc2dev.anga.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.doc2dev.anga.databinding.ItemForecastBinding

class ForecastViewHolder(
    binding: ItemForecastBinding
): RecyclerView.ViewHolder(binding.root) {
    val dayTextView = binding.dayTextView
    val weatherIcon = binding.weatherIcon
    val tempTextView = binding.tempTextView
}