package com.ddd4.synesthesia.beer.presentation.ui.like.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindableAdapter
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.like.item.IHomeLikeViewModel

class HomeLikeListAdapter : BaseBindableAdapter<HomeLikeViewType, IHomeLikeViewModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IHomeLikeViewModel, ViewDataBinding> {
        return HomeLikeHolder.getViewHolder(parent, HomeLikeViewType.values()[viewType])
    }
}

enum class HomeLikeViewType {
    LIST,
    LOADING
}
