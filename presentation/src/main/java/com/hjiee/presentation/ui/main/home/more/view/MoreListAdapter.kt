package com.hjiee.presentation.ui.main.home.more.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindableAdapter
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.main.home.more.item.IMoreListViewModel

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
