package com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel

class StyleMiddleListAdapter :
    SimpleBindingListAdapter<StyleMiddleItemViewModel>(R.layout.item_filter_style_middle) {

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(view: View): BaseBindingViewHolder<StyleMiddleItemViewModel, ViewDataBinding> {
        return StyleMiddleItemViewHolder(view) as BaseBindingViewHolder<StyleMiddleItemViewModel, ViewDataBinding>
    }
}