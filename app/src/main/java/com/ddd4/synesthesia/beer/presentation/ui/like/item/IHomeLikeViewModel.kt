package com.ddd4.synesthesia.beer.presentation.ui.like.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.like.view.HomeLikeViewType

interface IHomeLikeViewModel : IViewTypeGetter<HomeLikeViewType> {
    override fun getViewType(): HomeLikeViewType {
        return when (this) {
            is HomeLikeListItemViewModel -> HomeLikeViewType.LIST
            else -> HomeLikeViewType.LOADING
        }
    }
}