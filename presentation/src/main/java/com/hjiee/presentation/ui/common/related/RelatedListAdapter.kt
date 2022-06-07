package com.hjiee.presentation.ui.common.related

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindableAdapter
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class RelatedListAdapter : BaseBindableAdapter<RelatedListViewType, IRelatedListViewModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IRelatedListViewModel, ViewDataBinding> {
        return RelatedListViewHolder.getViewHolder(parent, RelatedListViewType.values()[viewType])
    }
}

enum class RelatedListViewType {
    RELATED
}