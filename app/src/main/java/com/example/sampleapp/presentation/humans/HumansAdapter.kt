package com.example.sampleapp.presentation.humans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.databinding.ItemPeopleBinding


class HumansAdapter : ListAdapter<HumanEntity, HumansViewHolder>(HumansDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HumansViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HumansViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HumansViewHolder, position: Int) {
        val people = getItem(position)
        val binding = holder.binding
        binding.fullName.text = "${people.name} ${people.surname}"
    }

}