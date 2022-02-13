package com.hjiee.domain.usecase.beer

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetReviewUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend fun execute(beerId: Int): List<DomainEntity.Review> {
        return repository.getReview(beerId)
    }
}