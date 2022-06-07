package com.hjiee.presentation.ui.main.home.main.item

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.main.home.main.item.parent.award.BeerAwardItemViewHolder
import com.hjiee.presentation.ui.main.home.main.item.parent.list.BeerListItemViewHolder
import com.hjiee.presentation.ui.main.home.main.view.HomeViewType

abstract class HomeViewHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: HomeViewType
        ): HomeViewHolder<IHomeItemViewModel, ViewDataBinding> {
            return when (viewType) {
                HomeViewType.RECOMMEND -> BeerListItemViewHolder.newInstance(parent)
                HomeViewType.AWARD -> BeerAwardItemViewHolder.newInstance(parent)
            } as HomeViewHolder<IHomeItemViewModel, ViewDataBinding>
        }
    }
}