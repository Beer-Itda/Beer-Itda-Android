package com.hjiee.presentation.ui.main.home.more.view

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.common.loading.LoadingViewHolder
import com.hjiee.presentation.ui.main.home.more.item.IMoreListViewModel
import com.hjiee.presentation.ui.main.home.more.item.MoreListItemViewHolder

abstract class MoreListHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: MoreListViewType
        ): MoreListHolder<IMoreListViewModel, ViewDataBinding> {
            return when (viewType) {
                MoreListViewType.LIST -> MoreListItemViewHolder.newInstance(parent)
                MoreListViewType.LOADING -> LoadingViewHolder.newInstance(parent)
            } as MoreListHolder<IMoreListViewModel, ViewDataBinding>
        }
    }
}