package com.hjiee.presentation.ui.main.mypage.favorite.item

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter
import com.hjiee.presentation.ui.main.mypage.favorite.view.MyFavoriteViewType

interface IMyFavoriteViewModel : IViewTypeGetter<MyFavoriteViewType> {
    override fun getViewType(): MyFavoriteViewType {
        return when (this) {
            else -> MyFavoriteViewType.BEER
        }
    }
}