package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity

import com.ddd4.synesthesia.beer.data.model.AppConfig
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.IHomeItemViewModel

sealed class HomeActionEntity : ActionEntity() {
    object Refresh : HomeActionEntity()
    class UpdateList(val beer: List<IHomeItemViewModel>) : HomeActionEntity()
    class AppConfigSetting(val config: AppConfig?) : HomeActionEntity()
    object LoadMore : HomeActionEntity()
}