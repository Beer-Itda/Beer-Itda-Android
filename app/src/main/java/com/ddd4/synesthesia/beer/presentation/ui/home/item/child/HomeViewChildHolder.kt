package com.ddd4.synesthesia.beer.presentation.ui.home.item.child

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeChildViewType

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