package com.hjiee.presentation.ui.common.related

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

abstract class RelatedListViewHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: RelatedListViewType
        ): RelatedListViewHolder<IRelatedListViewModel, ViewDataBinding> {
            return when (viewType) {
                RelatedListViewType.RELATED -> RelatedItemViewViewHolder.newInstance(parent)
            } as RelatedListViewHolder<IRelatedListViewModel, ViewDataBinding>
        }
    }
}