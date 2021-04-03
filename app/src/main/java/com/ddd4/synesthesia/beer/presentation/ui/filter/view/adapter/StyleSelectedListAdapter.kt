package com.ddd4.synesthesia.beer.presentation.ui.filter.view.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.select.StyleSelectedItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.small.StyleSmallItemViewModel

class StyleSelectedListAdapter :
    SimpleBindingListAdapter<StyleSmallItemViewModel>(R.layout.item_selected_style) {

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(view: View): BaseBindingViewHolder<StyleSmallItemViewModel, ViewDataBinding> {
        return StyleSelectedItemViewHolder(view) as BaseBindingViewHolder<StyleSmallItemViewModel, ViewDataBinding>
    }
}