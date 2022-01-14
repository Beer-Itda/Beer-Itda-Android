package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemLevelGuideBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

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