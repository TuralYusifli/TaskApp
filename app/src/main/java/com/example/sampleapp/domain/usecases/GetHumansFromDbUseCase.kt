package com.example.sampleapp.domain.usecases

import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.domain.repository.SampleRepository
import javax.inject.Inject

class GetHumansFromDbUseCase @Inject constructor(private val sampleRepository: SampleRepository) {

    suspend operator fun invoke(): List<HumanEntity> {
        return sampleRepository.getAllHumansFromDb()
    }
}