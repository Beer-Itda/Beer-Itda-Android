package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindableAdapter
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.home.item.child.HomeViewChildHolder
import com.ddd4.synesthesia.beer.presentation.ui.home.item.child.IHomeItemChildViewModel

class HomeListChildAdapter : BaseBindableAdapter<HomeChildViewType, IHomeItemChildViewModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IHomeItemChildViewModel, ViewDataBinding> {
        return HomeViewChildHolder.getViewHolder(parent, HomeChildViewType.values()[viewType])
    }
}

enum class HomeChildViewType {
    CHOICE,
}