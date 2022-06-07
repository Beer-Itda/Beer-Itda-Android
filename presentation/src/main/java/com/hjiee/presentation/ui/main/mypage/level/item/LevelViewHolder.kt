package com.hjiee.presentation.ui.main.mypage.level.item

import android.view.View
import com.hjiee.presentation.databinding.ItemLevelGuideBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class LevelViewHolder(
    itemView: View
) : BaseBindingViewHolder<LevelItemViewModel, ItemLevelGuideBinding>(itemView) {

    override fun onBind(viewModel: LevelItemViewModel, position: Int) {
        binding?.apply {
            this.viewModel = viewModel
            executePendingBindings()
        }
    }
}