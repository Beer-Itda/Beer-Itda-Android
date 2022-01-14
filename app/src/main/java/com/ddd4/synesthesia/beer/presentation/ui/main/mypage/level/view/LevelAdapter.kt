package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.view

import android.view.View
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item.LevelItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item.LevelViewHolder


class LevelAdapter :
    SimpleBindingListAdapter<LevelItemViewModel>(R.layout.item_level_guide) {

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(view: View): BaseBindingViewHolder<LevelItemViewModel, ViewDataBinding> {
        return LevelViewHolder(view) as BaseBindingViewHolder<LevelItemViewModel, ViewDataBinding>
    }
}