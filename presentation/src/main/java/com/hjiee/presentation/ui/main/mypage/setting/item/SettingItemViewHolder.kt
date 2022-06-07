package com.hjiee.presentation.ui.main.mypage.setting.item

import android.view.View
import com.hjiee.presentation.databinding.LayoutMyPageSettingBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class SettingItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<SettingItemViewModel, LayoutMyPageSettingBinding>(itemView) {

    override fun onBind(viewModel: SettingItemViewModel, position: Int) {
        binding?.apply {
            my = viewModel
        }
    }
}