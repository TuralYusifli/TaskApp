package com.example.sampleapp.domain.usecases

import com.example.sampleapp.common.State
import com.example.sampleapp.data.local.HumanEntity
import com.example.sampleapp.domain.repository.SampleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshDataUseCase @Inject constructor(private val sampleRepository: SampleRepository) {

    suspend operator fun invoke(): Flow<State<List<HumanEntity>>> {
        return sampleRepository.refreshData()
    }
}