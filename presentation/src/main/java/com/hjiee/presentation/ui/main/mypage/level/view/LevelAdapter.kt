package com.hjiee.presentation.ui.main.mypage.level.view

import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.R
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.hjiee.presentation.ui.main.mypage.level.item.LevelItemViewModel
import com.hjiee.presentation.ui.main.mypage.level.item.LevelViewHolder


class LevelAdapter :
    SimpleBindingListAdapter<LevelItemViewModel>(R.layout.item_level_guide) {

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(view: View): BaseBindingViewHolder<LevelItemViewModel, ViewDataBinding> {
        return LevelViewHolder(view) as BaseBindingViewHolder<LevelItemViewModel, ViewDataBinding>
    }
}