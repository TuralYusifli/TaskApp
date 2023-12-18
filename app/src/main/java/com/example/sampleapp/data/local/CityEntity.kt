package com.example.sampleapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey
    var cityId: Int,
    var name: String,
    var countryId: Int
)
