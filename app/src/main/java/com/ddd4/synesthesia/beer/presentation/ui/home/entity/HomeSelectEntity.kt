package com.ddd4.synesthesia.beer.presentation.ui.home.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class HomeSelectEntity : ItemClickEntity() {
    class Favorite(id : Int, flag : Boolean) : HomeSelectEntity()
    object Filter : HomeSelectEntity()
    object Search : HomeSelectEntity()
    object MyPage : HomeSelectEntity()
    object Sort : HomeSelectEntity()
}