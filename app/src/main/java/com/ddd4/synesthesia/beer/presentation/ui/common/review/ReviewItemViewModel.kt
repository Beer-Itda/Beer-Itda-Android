package com.ddd4.synesthesia.beer.presentation.ui.common.review

import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

class ReviewItemViewModel(
    val review: DomainEntity.Review?,
    val writerNickName: String = "",
    eventNotifier: SelectEventNotifier
) : IReviewListViewModel {

}