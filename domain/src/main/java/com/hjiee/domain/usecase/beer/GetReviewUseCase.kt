package com.hjiee.domain.usecase.beer

import com.hjiee.core.ext.orZero
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.ApiServiceConstants.DEFAULT_FIRST_PAGE
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class GetReviewUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend fun execute(beerId: Int, next: Int = DEFAULT_FIRST_PAGE): DomainEntity.PageResult<DomainEntity.Review> {
        return repository.getReview(beerId, next.orZero())
    }
}