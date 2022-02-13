package com.ddd4.synesthesia.beer.presentation.ui.detail.item.review

import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModelMapper.getReviewListItemViewModel
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.core.ext.orZero
import com.hjiee.domain.entity.DomainEntity

object BeerDetailReviewMapper {

    fun DomainEntity.BeerDetail?.getReviewListItemViewModel(
        eventNotifier: SelectEventNotifier
    ): BeerDetailReviewListViewModel {
        return BeerDetailReviewListViewModel(
            beerId = this?.beer?.id.orZero(),
            review = getReviewListItemViewModel(this?.review).take(5),
            myReview = ReviewItemViewModel(
                review = this?.myReview
            ),
            eventNotifier = eventNotifier
        )
    }

//    private fun List<DomainEntity.Review>.getReviewItemViewModel(): List<ReviewItemViewModel> {
//        return map {
//            ReviewItemViewModel(
//                reviewId = it.userId.orZero(),
//                writerNickName = it.nickname.orEmpty(),
//                ratio = it.ratio.orZero(),
//                content = it.content.orEmpty(),
//                createdAt = SimpleDateFormat("yyyy. MM. dd").format(it.date)
//            )
//        }
//    }
}