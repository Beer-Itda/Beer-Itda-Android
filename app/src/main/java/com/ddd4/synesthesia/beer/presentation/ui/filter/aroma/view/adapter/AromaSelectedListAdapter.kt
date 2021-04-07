package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.select.AromaSelectedItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel

class AromaSelectedListAdapter :
    SimpleBindingListAdapter<AromaItemViewModel>(R.layout.item_filter_selected_aroma) {

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(view: View): BaseBindingViewHolder<AromaItemViewModel, ViewDataBinding> {
        return AromaSelectedItemViewHolder(view) as BaseBindingViewHolder<AromaItemViewModel, ViewDataBinding>
    }
}