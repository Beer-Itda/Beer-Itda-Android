package com.hjiee.presentation.ui.main.home.main.entity

import com.hjiee.presentation.ui.main.home.main.item.IHomeItemViewModel
import com.hjiee.presentation.ui.main.home.main.item.parent.list.BeerListItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class HomeActionEntity : ActionEntity() {
    object Refresh : HomeActionEntity()
    class UpdateList(val beer: List<IHomeItemViewModel>) : HomeActionEntity()
//    class AppConfigSetting(val config: AppConfig?) : HomeActionEntity()
    class LoadMore(val item: BeerListItemViewModel) : HomeActionEntity()
}