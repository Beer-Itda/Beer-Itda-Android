package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item

import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.model.MyReviewSelectEntity
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

data class MyReviewItemViewModel(
    val beer: DomainEntity.Beer,
    val review: DomainEntity.Review,
    private val eventNotifier: SelectEventNotifier
) {
    fun clickItem() {
        eventNotifier.notifySelectEvent(MyReviewSelectEntity.SelectMyReview(this))
    }
}