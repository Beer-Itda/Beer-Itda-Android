package com.hjiee.presentation.ui.main.mypage.favorite.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindableAdapter
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.main.mypage.favorite.item.IMyFavoriteViewModel
import com.hjiee.presentation.ui.main.mypage.favorite.item.MyFavoriteViewHolder

class MyFavoriteAdapter : BaseBindableAdapter<MyFavoriteViewType, IMyFavoriteViewModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IMyFavoriteViewModel, ViewDataBinding> {
        return MyFavoriteViewHolder.getViewHolder(parent, MyFavoriteViewType.values()[viewType])
    }
}

enum class MyFavoriteViewType {
    BEER
}