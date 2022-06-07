package com.hjiee.presentation.ui.filter.style.view.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.R
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewHolder
import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

class StyleSmallListAdapter :
    SimpleBindingListAdapter<StyleSmallItemViewModel>(R.layout.item_filter_style_small) {

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(view: View): BaseBindingViewHolder<StyleSmallItemViewModel, ViewDataBinding> {
        return StyleSmallItemViewHolder(view) as BaseBindingViewHolder<StyleSmallItemViewModel, ViewDataBinding>
    }
}