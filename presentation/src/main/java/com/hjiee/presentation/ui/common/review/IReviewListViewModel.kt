package com.hjiee.presentation.ui.common.review

import com.hjiee.presentation.base.recyclerview2.IViewTypeGetter

interface IReviewListViewModel: IViewTypeGetter<ReviewListViewType> {

    override fun getViewType(): ReviewListViewType {
        return when (this) {
            is ReviewItemViewModel -> ReviewListViewType.REVIEW
            else -> ReviewListViewType.REVIEW
        }
    }
}