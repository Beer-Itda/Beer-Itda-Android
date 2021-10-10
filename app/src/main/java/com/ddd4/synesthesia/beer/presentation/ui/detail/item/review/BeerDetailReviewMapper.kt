package com.ddd4.synesthesia.beer.presentation.ui.detail.item.review

import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.ext.orDefault
import com.ddd4.synesthesia.beer.ext.orZero
import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemViewModel

object BeerDetailReviewMapper {

    fun BeerDetailItemViewModel.getReviewListItemViewModel(eventNotifier: SelectEventNotifier): BeerDetailReviewListViewModel {
        return BeerDetailReviewListViewModel(
            item = this,
            review = beer.reviews?.getReviewItemViewModel()?.take(5).orEmpty(),
            myReview = ReviewItemViewModel(
                reviewId = myReview?.reviewId.orZero(),
                writerNickName = myReview?.writerNickName.orEmpty(),
                ratio = myReview?.ratio.orDefault(0.5f),
                content = myReview?.content.orEmpty(),
                createdAt = "",
            ),
            eventNotifier = eventNotifier
        )
    }

    private fun List<Review>.getReviewItemViewModel(): List<ReviewItemViewModel> {
        return map {
            ReviewItemViewModel(
                reviewId = it.userId.orZero(),
                writerNickName = it.nickname.orEmpty(),
                ratio = it.ratio.orZero(),
                content = it.content.orEmpty(),
                createdAt = it.createdAt.orEmpty()
            )
        }
    }
}