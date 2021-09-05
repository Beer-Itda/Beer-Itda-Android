package com.ddd4.synesthesia.beer.presentation.ui.home.more.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindableAdapter
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.home.more.item.IMoreListViewModel

class MoreListAdapter : BaseBindableAdapter<MoreListViewType, IMoreListViewModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IMoreListViewModel, ViewDataBinding> {
        return MoreListHolder.getViewHolder(parent, MoreListViewType.values()[viewType])
    }
}

enum class MoreListViewType {
    LIST,
    LOADING
}
