package com.example.sampleapp.data.remote

import com.example.sampleapp.data.local.CountryEntity

data class CountryResponse(
    var countryId: Int,
    var name: String,
    var cityList: ArrayList<CityResponse> = arrayListOf()
)

fun List<CountryResponse>.toCountryEntity(): List<CountryEntity> = this.map {
    CountryEntity(it.countryId, it.name)
}
