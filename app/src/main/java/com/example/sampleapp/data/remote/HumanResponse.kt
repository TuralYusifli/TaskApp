package com.example.sampleapp.data.remote

import com.example.sampleapp.data.local.HumanEntity

data class HumanResponse(
    var humanId: Int,
    var name: String,
    var surname: String
)
fun List<HumanResponse>.toPeopleEntity(city: CityResponse, country: CountryResponse): List<HumanEntity> =
    this.map {
        HumanEntity(
            it.humanId,
            it.name,
            it.surname,
            city.cityId,
            country.countryId
        )
    }