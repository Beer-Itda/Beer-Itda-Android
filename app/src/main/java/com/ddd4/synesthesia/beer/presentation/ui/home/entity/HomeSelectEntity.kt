package com.ddd4.synesthesia.beer.presentation.ui.home.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeStringProvider

sealed class HomeSelectEntity : ItemClickEntity() {
    class Favorite(id : Int, flag : Boolean) : HomeSelectEntity()
    object ClickFilter : HomeSelectEntity()
    object Search : HomeSelectEntity()
    object MyPage : HomeSelectEntity()
    object Sort : HomeSelectEntity()
    class ClickTitle(val type : HomeStringProvider.Code) : HomeSelectEntity()
}