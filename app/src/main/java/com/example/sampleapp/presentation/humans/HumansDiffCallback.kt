package com.example.sampleapp.presentation.humans

import androidx.recyclerview.widget.DiffUtil
import com.example.sampleapp.data.local.HumanEntity

class HumansDiffCallback : DiffUtil.ItemCallback<HumanEntity>() {

    override fun areItemsTheSame(oldItem: HumanEntity, newItem: HumanEntity): Boolean {
        return oldItem.humanId == newItem.humanId
    }

    override fun areContentsTheSame(oldItem: HumanEntity, newItem: HumanEntity): Boolean {
        return oldItem == newItem
    }
}