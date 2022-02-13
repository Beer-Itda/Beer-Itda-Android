package com.ddd4.synesthesia.beer.presentation.ui.common.review

import com.hjiee.domain.entity.DomainEntity

class ReviewItemViewModel(
    val review: DomainEntity.Review?,
    val writerNickName: String = ""
) : IReviewListViewModel {

}