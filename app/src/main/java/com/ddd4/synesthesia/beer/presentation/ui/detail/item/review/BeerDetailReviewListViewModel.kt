package com.ddd4.synesthesia.beer.presentation.ui.detail.item.review

import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.common.review.model.ReviewItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.core.ext.orZero

class BeerDetailReviewListViewModel(
    val beerId: Int,
    val review: List<ReviewItemViewModel>?,
    val myReview: ReviewItemViewModel?,
    val eventNotifier: SelectEventNotifier
) : IBeerDetailViewModel {
    val isMyReviewExist: Boolean = myReview != null && myReview.review?.reviewId.orZero() != 0
    val adapter = ReviewListAdapter()
    val reviewCount = review?.size?.toFloat().orZero()

    init {
        adapter.clear()
        adapter.addAll(review.orEmpty())
    }

    fun clickShowMoreReview() {
        eventNotifier.notifySelectEvent(
            BeerDetailItemSelectEntity.ReviewAll(
                beerId = beerId,
                reviewCount = review?.size.orZero()
            )
        )
    }

    fun clickWriteReview() {
        eventNotifier.notifySelectEvent(ReviewItemSelectEntity.WriteReview(beerId))
    }

    fun clickMyReviewEdit() {
        if (isMyReviewExist) {
            eventNotifier.notifySelectEvent(ReviewItemSelectEntity.EditReview(myReview?.review?.reviewId.orZero()))
        }
    }
}