package com.ddd4.synesthesia.beer.presentation.ui.detail.item

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter
import com.ddd4.synesthesia.beer.presentation.ui.detail.adapter.DetailViewType
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.info.BeerDetailInfoItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.review.BeerDetailReviewListViewModel

interface IBeerDetailViewModel: IViewTypeGetter<DetailViewType> {

    override fun getViewType(): DetailViewType {
        return when (this) {
            is BeerDetailInfoItemViewModel -> DetailViewType.INFORMATION
            is BeerDetailReviewListViewModel -> DetailViewType.REVIEW
            else -> DetailViewType.RELATED
        }
    }
}