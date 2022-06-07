package com.hjiee.presentation.ui.detail.item.review

import com.hjiee.presentation.ui.common.review.ReviewItemViewModel
import com.hjiee.presentation.ui.common.review.ReviewListAdapter
import com.hjiee.presentation.ui.common.review.model.ReviewItemSelectEntity
import com.hjiee.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.hjiee.presentation.ui.detail.item.IBeerDetailViewModel
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.core.ext.orZero

class BeerDetailReviewListViewModel(
    val beerId: Int,
    val reviewCount: Float,
    val review: List<ReviewItemViewModel>?,
    val myReview: ReviewItemViewModel?,
    val eventNotifier: SelectEventNotifier
) : IBeerDetailViewModel {

    val isMyReviewExist: Boolean = myReview != null && myReview.review?.reviewId.orZero() != 0
//    val isShowMoreReview = reviewCount > FIRST_SHOW_REVIEW_COUNT
    val isShowMoreReview = true

    val adapter = ReviewListAdapter()

    init {
        adapter.clear()
        adapter.addAll(review.orEmpty())
    }

    fun clickShowMoreReview() {
        if (isShowMoreReview) {
            eventNotifier.notifySelectEvent(
                BeerDetailItemSelectEntity.ReviewAll(
                    beerId = beerId,
                    reviewCount = review?.size.orZero()
                )
            )
        }
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