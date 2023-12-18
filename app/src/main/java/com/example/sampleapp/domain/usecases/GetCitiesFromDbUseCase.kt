package com.example.sampleapp.domain.usecases

import com.example.sampleapp.data.local.CityEntity
import com.example.sampleapp.domain.repository.SampleRepository
import javax.inject.Inject

class GetCitiesFromDbUseCase @Inject constructor(private val sampleRepository: SampleRepository) {

    suspend operator fun invoke(): List<CityEntity> {
        return sampleRepository.getAllCitiesFromDb()
    }
}