package com.hjiee.domain.usecase.beer

import com.hjiee.core.util.log.L
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetAwardBeerUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(): DomainEntity.Beer? {
        return try {
            repository.getBeerAward()?.data
        } catch (e: Exception) {
            L.e(e)
            null
        }
    }
}