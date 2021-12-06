package com.ddd4.synesthesia.beer.presentation.ui.detail.item.review

import com.hjiee.core.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.common.review.model.ReviewItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel

class BeerDetailReviewListViewModel(
//    val item: BeerDetailItemViewModel,
    val review: List<ReviewItemViewModel>?,
    val myReview: ReviewItemViewModel?,
    val eventNotifier: SelectEventNotifier
) : IBeerDetailViewModel {
    val isMyReviewExist: Boolean = myReview != null && myReview.reviewId != 0
    val adapter = ReviewListAdapter()
    val reviewCount = review?.size?.toFloat()

    init {
        adapter.clear()
        adapter.addAll(review.orEmpty())
    }

    fun clickShowMoreReview() {
        eventNotifier.notifySelectEvent(BeerDetailItemSelectEntity.ReviewAll)
    }

    fun clickWriteReview() {
//        eventNotifier.notifySelectEvent(ReviewItemSelectEntity.WriteReview(item.beer.id))
    }
}