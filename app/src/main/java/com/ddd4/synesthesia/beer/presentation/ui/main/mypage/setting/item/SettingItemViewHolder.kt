package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item

import android.view.View
import com.ddd4.synesthesia.beer.databinding.LayoutMyPageSettingBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

class SettingItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<SettingItemViewModel, LayoutMyPageSettingBinding>(itemView) {

    override fun onBind(viewModel: SettingItemViewModel, position: Int) {
        binding?.apply {
            my = viewModel
        }
    }
}