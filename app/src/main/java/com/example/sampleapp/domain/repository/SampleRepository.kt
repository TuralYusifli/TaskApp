package com.example.sampleapp.domain.repository

import com.example.sampleapp.common.State
import com.example.sampleapp.data.local.CityEntity
import com.example.sampleapp.data.local.CountryEntity
import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.data.remote.CountryResponse
import kotlinx.coroutines.flow.Flow

interface SampleRepository {

    suspend fun refreshData(): Flow<State<List<HumanEntity>>>

    suspend fun getAllHumansFromDb(): List<HumanEntity>

    suspend fun getAllCountriesFromDb(): List<CountryEntity>

    suspend fun getAllCitiesFromDb(): List<CityEntity>

    suspend fun insertCountries(countryResponse: List<CountryResponse>)

    suspend fun insertCities(countryResponse: List<CountryResponse>)

    suspend fun insertHumans(countryResponse: List<CountryResponse>)

    suspend fun getHumansByCities(cities: List<CityEntity>): List<HumanEntity>

    suspend fun getHumansByCountries(countries: List<CountryEntity>): List<HumanEntity>

    suspend fun getCitiesByCountries(countries: List<CountryEntity>): List<CityEntity>

}