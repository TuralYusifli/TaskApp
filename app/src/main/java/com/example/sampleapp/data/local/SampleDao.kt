package com.example.sampleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(countries: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHumans(countries: List<HumanEntity>)

    @Query("SELECT * FROM HumanEntity")
    suspend fun getAllHumans(): List<HumanEntity>

    @Query("SELECT * FROM CountryEntity")
    suspend fun getAllCountries(): List<CountryEntity>

    @Query("SELECT * FROM CityEntity")
    suspend fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM CityEntity WHERE countryId IN (:countryIds)")
    fun getCitiesByCountries(countryIds: List<Int>): List<CityEntity>

    @Query("SELECT * FROM HumanEntity WHERE cityId IN (:cityIds)")
    fun getHumansByCities(cityIds: List<Int>): List<HumanEntity>

    @Query("SELECT * FROM HumanEntity WHERE countryId IN (:countryIds)")
    fun getHumansByCountries(countryIds: List<Int>): List<HumanEntity>
}