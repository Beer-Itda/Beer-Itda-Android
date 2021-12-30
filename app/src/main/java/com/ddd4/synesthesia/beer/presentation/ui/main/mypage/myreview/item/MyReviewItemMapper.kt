package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item

import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object MyReviewItemMapper {

    fun List<DomainEntity.MyReview>.getMapperItem(
        eventNotifier: SelectEventNotifier
    ): List<MyReviewItemViewModel> {
        return this.map {
            MyReviewItemViewModel(
                beer = it.beer,
                review = it.review,
                eventNotifier = eventNotifier
            )
        }
    }
}