package com.ddd4.synesthesia.beer.presentation.ui.like.view

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.common.loading.LoadingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.like.item.HomeLikeItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.like.item.IHomeLikeViewModel

abstract class HomeLikeHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: HomeLikeViewType
        ): HomeLikeHolder<IHomeLikeViewModel, ViewDataBinding> {
            return when (viewType) {
                HomeLikeViewType.LIST -> HomeLikeItemViewHolder.newInstance(parent)
                HomeLikeViewType.LOADING -> LoadingViewHolder.newInstance(parent)
            } as HomeLikeHolder<IHomeLikeViewModel, ViewDataBinding>
        }
    }
}