package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity

import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType

sealed class HomeSelectEntity : ItemClickEntity() {
    object ClickFilter : HomeSelectEntity()
    object Search : HomeSelectEntity()
    object MyPage : HomeSelectEntity()
    object Sort : HomeSelectEntity()
    class ClickTitle(
        val type: HomeStringProvider.Code,
        val title: String
    ) : HomeSelectEntity()
}