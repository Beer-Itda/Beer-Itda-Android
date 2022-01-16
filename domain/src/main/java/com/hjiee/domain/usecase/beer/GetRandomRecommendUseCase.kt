package com.hjiee.domain.usecase.beer

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetRandomRecommendUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(): DomainEntity.Beers? {
        return repository.getRandomRecommendBeer()?.data
    }
}