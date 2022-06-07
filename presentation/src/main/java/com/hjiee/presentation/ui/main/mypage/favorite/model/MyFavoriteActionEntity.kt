package com.hjiee.presentation.ui.main.mypage.favorite.model

import com.hjiee.presentation.ui.main.mypage.favorite.item.MyFavoriteItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class MyFavoriteActionEntity : ActionEntity() {
    class UpdateUi(val viewModel: List<MyFavoriteItemViewModel>) : MyFavoriteActionEntity()
    object Refresh : MyFavoriteActionEntity()
}