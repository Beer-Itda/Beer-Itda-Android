package com.ddd4.synesthesia.beer.presentation.ui.common.related

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter

interface IRelatedListViewModel: IViewTypeGetter<RelatedListViewType> {

    override fun getViewType(): RelatedListViewType {
        return when (this) {
            is RelatedItemViewModel -> RelatedListViewType.RELATED
            else -> RelatedListViewType.RELATED
        }
    }
}