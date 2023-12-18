package com.example.sampleapp.presentation.city

import androidx.recyclerview.widget.DiffUtil
import com.example.sampleapp.data.local.CityEntity

class CityDiffCallback : DiffUtil.ItemCallback<CityEntity>() {

    override fun areItemsTheSame(oldItem: CityEntity, newItem: CityEntity): Boolean {
        return oldItem.cityId == newItem.cityId
    }

    override fun areContentsTheSame(oldItem: CityEntity, newItem: CityEntity): Boolean {
        return oldItem == newItem
    }
}