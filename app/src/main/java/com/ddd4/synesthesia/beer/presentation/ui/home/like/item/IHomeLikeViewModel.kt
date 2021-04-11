package com.ddd4.synesthesia.beer.presentation.ui.home.like.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.home.like.view.HomeLikeViewType

interface IHomeLikeViewModel : IViewTypeGetter<HomeLikeViewType> {
    override fun getViewType(): HomeLikeViewType {
        return when (this) {
            is HomeLikeListItemViewModel -> HomeLikeViewType.LIST
            else -> HomeLikeViewType.LOADING
        }
    }
}