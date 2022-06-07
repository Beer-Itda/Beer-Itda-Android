package com.hjiee.presentation.ui.detail.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindableAdapter
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.detail.item.IBeerDetailViewModel

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