package com.example.sampleapp.domain.usecases

import com.example.sampleapp.data.local.CityEntity
import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.domain.repository.SampleRepository
import javax.inject.Inject

class GetHumansByCitiesUseCase @Inject constructor(private val sampleRepository: SampleRepository) {

    suspend operator fun invoke(cities: List<CityEntity>): List<HumanEntity> {
        return sampleRepository.getHumansByCities(cities)
    }
}