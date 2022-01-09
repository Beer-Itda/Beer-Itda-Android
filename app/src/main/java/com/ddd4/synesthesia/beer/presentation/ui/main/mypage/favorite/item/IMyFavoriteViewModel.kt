package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.view.MyFavoriteViewType

interface IMyFavoriteViewModel : IViewTypeGetter<MyFavoriteViewType> {
    override fun getViewType(): MyFavoriteViewType {
        return when (this) {
            else -> MyFavoriteViewType.BEER
        }
    }
}