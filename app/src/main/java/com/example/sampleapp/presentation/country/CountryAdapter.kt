package com.example.sampleapp.presentation.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.sampleapp.data.local.CountryEntity
import com.example.sampleapp.databinding.ItemCountryBinding

class CountryAdapter(
    private val clickListener: CountryRecyclerViewClickListener
) : ListAdapter<CountryEntity, CountryViewHolder>(CountryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = getItem(position)
        val binding = holder.binding
        binding.countryName.text = country.name
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            clickListener.clickOnCountryCheckbox(country, isChecked)
        }
    }

    interface CountryRecyclerViewClickListener {
        fun clickOnCountryCheckbox(country: CountryEntity, isChecked: Boolean)
    }

}

