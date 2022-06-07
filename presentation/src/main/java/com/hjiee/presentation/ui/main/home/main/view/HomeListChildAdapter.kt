package com.hjiee.presentation.ui.main.home.main.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindableAdapter
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.main.home.main.item.child.HomeViewChildHolder
import com.hjiee.presentation.ui.main.home.main.item.child.IHomeItemChildViewModel

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