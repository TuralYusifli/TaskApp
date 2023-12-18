package com.example.sampleapp.data.remote

import com.example.sampleapp.data.local.CityEntity

data class CityResponse(
    var cityId: Int,
    var name: String,
    var peopleList: ArrayList<HumanResponse> = arrayListOf()
)

fun List<CityResponse>.toCityEntity(country: CountryResponse): List<CityEntity> =
    this.map {
        CityEntity(it.cityId, it.name, country.countryId)
    }
