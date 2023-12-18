package com.example.sampleapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    @PrimaryKey
    var countryId: Int,
    var name: String
)