package com.hjiee.domain.usecase.beer

import com.hjiee.core.ext.orZero
import com.hjiee.core.util.log.L
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.ApiServiceConstants.DEFAULT_FIRST_PAGE
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetRandomRecommendUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(next: Int? = DEFAULT_FIRST_PAGE): DomainEntity.Beers? {
        return try {
            repository.getRandomRecommendBeer(next.orZero())?.data
        } catch (e: Exception) {
            L.e(e)
            null
        }
    }
}