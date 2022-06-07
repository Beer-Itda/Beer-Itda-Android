package com.hjiee.presentation.ui.main.mypage.favorite.item

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.ui.main.mypage.favorite.view.MyFavoriteViewType


abstract class MyFavoriteViewHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: MyFavoriteViewType
        ): MyFavoriteViewHolder<IMyFavoriteViewModel, ViewDataBinding> {
            return when (viewType) {
                MyFavoriteViewType.BEER -> MyFavoriteItemViewHolder.newInstance(parent)
            } as MyFavoriteViewHolder<IMyFavoriteViewModel, ViewDataBinding>
        }
    }
}