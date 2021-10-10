package com.ddd4.synesthesia.beer.presentation.ui.common.review

import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.IViewTypeGetter

interface IReviewListViewModel: IViewTypeGetter<ReviewListViewType> {

    override fun getViewType(): ReviewListViewType {
        return when (this) {
            is ReviewItemViewModel -> ReviewListViewType.REVIEW
            else -> ReviewListViewType.REVIEW
        }
    }
}