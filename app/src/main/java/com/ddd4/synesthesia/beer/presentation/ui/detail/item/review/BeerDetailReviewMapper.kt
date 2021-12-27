//package com.ddd4.synesthesia.beer.presentation.ui.detail.item.review
//
//import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModel
//import com.hjiee.core.event.SelectEventNotifier
//import com.hjiee.domain.entity.DomainEntity
//
//object BeerDetailReviewMapper {
//
//    fun DomainEntity.BeerDetail?.getReviewListItemViewModel(
//        eventNotifier: SelectEventNotifier
//    ): BeerDetailReviewListViewModel {
//        return BeerDetailReviewListViewModel(
////            item = this,
//            review = null,
////            review = beer.reviews?.getReviewItemViewModel()?.take(5).orEmpty(),
//            myReview = ReviewItemViewModel(
////                reviewId = myReview?.reviewId.orZero(),
////                writerNickName = myReview?.writerNickName.orEmpty(),
////                ratio = myReview?.ratio.orDefault(0.5f),
////                content = myReview?.content.orEmpty(),
//                createdAt = "",
//            ),
//            eventNotifier = eventNotifier
//        )
//    }
//
////    private fun List<DomainEntity.Review>.getReviewItemViewModel(): List<ReviewItemViewModel> {
////        return map {
////            ReviewItemViewModel(
////                reviewId = it.userId.orZero(),
////                writerNickName = it.nickname.orEmpty(),
////                ratio = it.ratio.orZero(),
////                content = it.content.orEmpty(),
////                createdAt = SimpleDateFormat("yyyy. MM. dd").format(it.date)
////            )
////        }
////    }
//}