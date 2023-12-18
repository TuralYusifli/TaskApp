package com.example.sampleapp.data.repository

import com.example.sampleapp.common.State
import com.example.sampleapp.data.local.CityEntity
import com.example.sampleapp.data.local.CountryEntity
import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.data.local.SampleDao
import com.example.sampleapp.data.remote.CountryResponse
import com.example.sampleapp.data.remote.SampleApi
import com.example.sampleapp.data.remote.toCityEntity
import com.example.sampleapp.data.remote.toCountryEntity
import com.example.sampleapp.data.remote.toPeopleEntity
import com.example.sampleapp.domain.repository.SampleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val remote: SampleApi,
    private val local: SampleDao

) : SampleRepository {

    override suspend fun refreshData(): Flow<State<List<HumanEntity>>> = flow {
        val response = remote.getAllData()
        if (response.isSuccessful && !response.body()?.countryList.isNullOrEmpty()) {
            cashingRemoteData(response.body()!!.countryList)
            val peopleFromDb = getAllHumansFromDb()
            emit(State.Success(peopleFromDb))
        } else {
            emit(State.Error)
        }
    }.catch {
        it.printStackTrace()
        emit(State.Error)
    }

    override suspend fun getAllHumansFromDb() = withContext(Dispatchers.IO) {
        local.getAllHumans()
    }

    override suspend fun getAllCountriesFromDb() = withContext(Dispatchers.IO) {
        local.getAllCountries()
    }

    override suspend fun getAllCitiesFromDb() = withContext(Dispatchers.IO) {
        local.getAllCities()
    }

    private suspend fun cashingRemoteData(countryResponse: List<CountryResponse>) {
        insertCountries(countryResponse)
        insertCities(countryResponse)
        insertHumans(countryResponse)
    }

    override suspend fun insertCountries(countryResponse: List<CountryResponse>) {
        withContext(Dispatchers.IO) {
            local.insertCountries(countryResponse.toCountryEntity())
        }
    }

    override suspend fun insertCities(countryResponse: List<CountryResponse>) {
        withContext(Dispatchers.IO) {
            val cityEntities = countryResponse.flatMap { country ->
                country.cityList.toCityEntity(country)
            }
            local.insertCities(cityEntities)
        }
    }

    override suspend fun insertHumans(countryResponse: List<CountryResponse>) {
        withContext(Dispatchers.IO) {
            val peopleEntities = countryResponse.flatMap { country ->
                country.cityList.flatMap { city ->
                    city.peopleList.toPeopleEntity(city,country)
                }
            }
            local.insertHumans(peopleEntities)
        }
    }

    override suspend fun getHumansByCities(cities: List<CityEntity>) = withContext(Dispatchers.IO) {
        val cityIds = cities.map { it.cityId }
        local.getHumansByCities(cityIds)
    }

    override suspend fun getHumansByCountries(countries: List<CountryEntity>) = withContext(Dispatchers.IO) {
        val countryIds = countries.map { it.countryId }
        local.getHumansByCountries(countryIds)
    }

    override suspend fun getCitiesByCountries(countries: List<CountryEntity>) = withContext(Dispatchers.IO) {
        val countryIds = countries.map { it.countryId }
        local.getCitiesByCountries(countryIds)
    }
}