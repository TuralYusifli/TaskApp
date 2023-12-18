package com.example.sampleapp.domain.usecases

import com.example.sampleapp.data.local.CountryEntity
import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.domain.repository.SampleRepository
import javax.inject.Inject

class GetHumansByCountriesUseCase @Inject constructor(private val sampleRepository: SampleRepository) {

    suspend operator fun invoke(countries: List<CountryEntity>): List<HumanEntity> {
        return sampleRepository.getHumansByCountries(countries)
    }
}