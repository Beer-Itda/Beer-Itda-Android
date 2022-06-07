package com.hjiee.presentation.ui.main.mypage.myreview.model

import com.hjiee.presentation.ui.main.mypage.myreview.item.MyReviewItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity

sealed class MyReviewSelectEntity : ItemClickEntity() {
    class SelectMyReview(val item: MyReviewItemViewModel) : MyReviewSelectEntity()
}