package com.hjiee.presentation.ui.review.model

import com.hjiee.presentation.ui.common.review.ReviewItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class ReviewListActionEntity : ActionEntity() {
    class UpdateUi(val items: List<ReviewItemViewModel>) : ReviewListActionEntity()
}