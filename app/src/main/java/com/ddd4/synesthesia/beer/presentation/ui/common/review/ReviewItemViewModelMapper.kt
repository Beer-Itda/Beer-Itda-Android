package com.ddd4.synesthesia.beer.presentation.ui.common.review

import com.hjiee.domain.entity.DomainEntity

object ReviewItemViewModelMapper {

    fun getReviewListItemViewModel(reviewList: List<DomainEntity.Review>?): List<ReviewItemViewModel> {
        return reviewList?.map {
            ReviewItemViewModel(
                review = it
            )
        }.orEmpty()
    }
}