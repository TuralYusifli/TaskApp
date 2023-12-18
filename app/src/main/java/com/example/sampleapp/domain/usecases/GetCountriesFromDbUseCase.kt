package com.example.sampleapp.domain.usecases

import com.example.sampleapp.data.local.CountryEntity
import com.example.sampleapp.domain.repository.SampleRepository
import javax.inject.Inject

class GetCountriesFromDbUseCase @Inject constructor(private val sampleRepository: SampleRepository) {

    suspend operator fun invoke(): List<CountryEntity> {
        return sampleRepository.getAllCountriesFromDb()
    }
}