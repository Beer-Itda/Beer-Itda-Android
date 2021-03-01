package com.ddd4.synesthesia.beer.presentation.ui.home.entity

import com.ddd4.synesthesia.beer.data.model.AppConfig
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.home.item.IHomeItemViewModel

sealed class HomeActionEntity : ActionEntity() {
    class UpdateList(val beer: List<IHomeItemViewModel>) : HomeActionEntity()
    class FilterSetting(val filter : BeerFilter?) : HomeActionEntity()
    class AppConfigSetting(val config : AppConfig?) : HomeActionEntity()
    object LoadMore : HomeActionEntity()
}