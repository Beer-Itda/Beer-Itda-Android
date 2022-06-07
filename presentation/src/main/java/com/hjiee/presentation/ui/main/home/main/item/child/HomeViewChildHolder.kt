package com.hjiee.presentation.ui.main.home.main.item.child

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.main.home.main.view.HomeChildViewType

abstract class HomeViewChildHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: HomeChildViewType
        ): HomeViewChildHolder<IHomeItemChildViewModel, ViewDataBinding> {
            return when (viewType) {
                HomeChildViewType.CHOICE -> HomeItemViewHolder.newInstance(parent)
            } as HomeViewChildHolder<IHomeItemChildViewModel, ViewDataBinding>
        }
    }
}