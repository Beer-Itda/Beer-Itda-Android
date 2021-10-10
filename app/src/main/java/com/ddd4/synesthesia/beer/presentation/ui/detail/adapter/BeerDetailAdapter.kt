package com.ddd4.synesthesia.beer.presentation.ui.detail.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindableAdapter
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel

class DetailAdapter : BaseBindableAdapter<DetailViewType, IBeerDetailViewModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IBeerDetailViewModel, ViewDataBinding> {
        return BeerDetailViewHolder.getViewHolder(parent, DetailViewType.values()[viewType])
    }
}

enum class DetailViewType {
    INFORMATION,
    REVIEW,
    RELATED
}