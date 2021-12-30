package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.model

import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item.MyReviewItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class MyReviewActionEntity : ActionEntity() {
    class UpdateUi(val items: List<MyReviewItemViewModel>): MyReviewActionEntity()
}