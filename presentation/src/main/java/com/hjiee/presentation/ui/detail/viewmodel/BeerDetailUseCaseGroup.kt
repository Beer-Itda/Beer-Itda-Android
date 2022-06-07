package com.hjiee.presentation.ui.detail.viewmodel

import com.hjiee.domain.usecase.beer.PostFavoriteUseCase
import com.hjiee.domain.usecase.beer.detail.GetBeerDetailUseCase
import com.hjiee.domain.usecase.review.DeleteReviewUseCase
import javax.inject.Inject

class BeerDetailUseCaseGroup @Inject constructor(
    val getBeerDetail: GetBeerDetailUseCase,
    val favorite: PostFavoriteUseCase,
    val deleteReview: DeleteReviewUseCase
) {
}