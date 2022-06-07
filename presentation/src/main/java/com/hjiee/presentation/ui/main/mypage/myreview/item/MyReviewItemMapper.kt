package com.hjiee.presentation.ui.main.mypage.myreview.item

import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object MyReviewItemMapper {

    fun DomainEntity.PageResult<DomainEntity.MyReview>.getMapperItem(
        eventNotifier: SelectEventNotifier
    ): List<MyReviewItemViewModel> {
        return this.data.map {
            MyReviewItemViewModel(
                beer = it.beer,
                review = it.review,
                eventNotifier = eventNotifier
            )
        }
    }
}