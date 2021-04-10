package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class StarRatingBottomClickEntity : ItemClickEntity() {
    object ClickGuide : StarRatingBottomClickEntity()
}