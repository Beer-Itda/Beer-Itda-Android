package com.ddd4.synesthesia.beer.presentation.ui.common.related

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindableAdapter
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

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