package com.hjiee.domain.usecase.review

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class PostReviewUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(beerId: Int, starScore: Float, content: String) {
        repository.postReview(
            beerId = beerId,
            starScore = starScore,
            content = content
        )
    }
}