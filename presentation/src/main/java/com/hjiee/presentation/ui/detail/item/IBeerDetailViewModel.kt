package com.hjiee.presentation.ui.detail.item

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter
import com.hjiee.presentation.ui.detail.adapter.DetailViewType
import com.hjiee.presentation.ui.detail.item.info.BeerDetailInfoItemViewModel
import com.hjiee.presentation.ui.detail.item.review.BeerDetailReviewListViewModel

interface IBeerDetailViewModel: IViewTypeGetter<DetailViewType> {

    override fun getViewType(): DetailViewType {
        return when (this) {
            is BeerDetailInfoItemViewModel -> DetailViewType.INFORMATION
            is BeerDetailReviewListViewModel -> DetailViewType.REVIEW
            else -> DetailViewType.RELATED
        }
    }
}