package com.hjiee.presentation.ui.main.home.main.entity

import com.hjiee.presentation.ui.main.home.main.view.HomeBeerRecommendType
import com.hjiee.core.event.entity.ItemClickEntity

sealed class HomeSelectEntity : ItemClickEntity() {
    object ClickFilter : HomeSelectEntity()
    object Search : HomeSelectEntity()
    object MyPage : HomeSelectEntity()
    object Sort : HomeSelectEntity()
    class ClickTitle(
        val type: HomeBeerRecommendType,
        val title: String
    ) : HomeSelectEntity()
}