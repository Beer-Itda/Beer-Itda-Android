package com.hjiee.presentation.ui.common.related

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter

interface IRelatedListViewModel: IViewTypeGetter<RelatedListViewType> {

    override fun getViewType(): RelatedListViewType {
        return when (this) {
            is RelatedItemViewModel -> RelatedListViewType.RELATED
            else -> RelatedListViewType.RELATED
        }
    }
}