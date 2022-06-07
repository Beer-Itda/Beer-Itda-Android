package com.hjiee.presentation.ui.filter.style.view.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.R
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.hjiee.presentation.ui.filter.style.item.select.StyleSelectedItemViewHolder
import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

class StyleSelectedListAdapter :
    SimpleBindingListAdapter<StyleSmallItemViewModel>(R.layout.item_filter_selected_style) {

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(view: View): BaseBindingViewHolder<StyleSmallItemViewModel, ViewDataBinding> {
        return StyleSelectedItemViewHolder(view) as BaseBindingViewHolder<StyleSmallItemViewModel, ViewDataBinding>
    }
}