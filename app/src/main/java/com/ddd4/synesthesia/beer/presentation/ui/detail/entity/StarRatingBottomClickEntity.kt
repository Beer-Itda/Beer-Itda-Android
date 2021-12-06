package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.hjiee.core.event.entity.ItemClickEntity

sealed class StarRatingBottomClickEntity : ItemClickEntity() {
    object ClickGuide : StarRatingBottomClickEntity()
}