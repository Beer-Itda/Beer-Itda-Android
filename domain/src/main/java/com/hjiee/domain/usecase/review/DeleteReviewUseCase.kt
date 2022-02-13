package com.hjiee.domain.usecase.review

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class DeleteReviewUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(reviewId: Int) {
        repository.deleteReview(reviewId)
    }
}