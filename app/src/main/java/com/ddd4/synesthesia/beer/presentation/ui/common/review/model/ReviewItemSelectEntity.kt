package com.ddd4.synesthesia.beer.presentation.ui.common.review.model

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class ReviewItemSelectEntity : ItemClickEntity() {
    /** 맥주 리뷰쓰기 */
    class WriteReview(val beerId: Int) : ReviewItemSelectEntity()
}