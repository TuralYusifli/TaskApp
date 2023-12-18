package com.example.sampleapp.presentation.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.sampleapp.data.local.CityEntity
import com.example.sampleapp.databinding.ItemCityBinding

class CityAdapter(
    private val clickListener: CityRecyclerViewClickListener
) : ListAdapter<CityEntity, CityViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)
        val binding = holder.binding
        binding.cityName.text = city.name
        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            clickListener.ClickOnCityCheckbox(city, isChecked)
        }
    }

    interface CityRecyclerViewClickListener {
        fun ClickOnCityCheckbox(country: CityEntity, isChecked: Boolean)
    }

}

