package com.ddd4.synesthesia.beer.presentation.ui.common.review.model

import com.hjiee.core.event.entity.ItemClickEntity

sealed class ReviewItemSelectEntity : ItemClickEntity() {
    /** 맥주 리뷰쓰기 */
    class WriteReview(val beerId: Int) : ReviewItemSelectEntity()

    /** 리뷰 수정/삭제 */
    class EditReview(val reviewId: Int) : ReviewItemSelectEntity()
}