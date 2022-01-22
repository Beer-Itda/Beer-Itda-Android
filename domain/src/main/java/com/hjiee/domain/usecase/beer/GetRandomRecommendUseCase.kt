package com.hjiee.domain.usecase.beer

import com.hjiee.core.util.log.L
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetRandomRecommendUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(): List<DomainEntity.Beer> {
        return try {
            repository.getRandomRecommendBeer()?.data?.beers.orEmpty()
        } catch (e: Exception) {
            L.e(e)
            emptyList()
        }
    }
}