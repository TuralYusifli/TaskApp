package com.example.sampleapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HumanEntity(
    @PrimaryKey
    var humanId: Int,
    var name: String,
    var surname: String,
    var cityId: Int,
    var countryId: Int
)
