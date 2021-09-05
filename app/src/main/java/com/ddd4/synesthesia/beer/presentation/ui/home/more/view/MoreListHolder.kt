package com.ddd4.synesthesia.beer.presentation.ui.home.more.view

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.common.loading.LoadingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.home.more.item.MoreListItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.home.more.item.IMoreListViewModel

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