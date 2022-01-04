package com.hjiee.domain.usecase.filter.aroma

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetAromaUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute() {
        repository.getAromaInfo()
    }
}