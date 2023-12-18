package com.example.sampleapp.presentation.country

import androidx.recyclerview.widget.DiffUtil
import com.example.sampleapp.data.local.CountryEntity

class CountryDiffCallback : DiffUtil.ItemCallback<CountryEntity>() {

    override fun areItemsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
        return oldItem.countryId == newItem.countryId
    }

    override fun areContentsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
        return oldItem == newItem
    }
}