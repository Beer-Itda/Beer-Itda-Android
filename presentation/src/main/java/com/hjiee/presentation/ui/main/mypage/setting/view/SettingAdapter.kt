package com.hjiee.presentation.ui.main.mypage.setting.view

import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.R
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.hjiee.presentation.ui.main.mypage.setting.item.SettingItemViewHolder
import com.hjiee.presentation.ui.main.mypage.setting.item.SettingItemViewModel

class SettingAdapter : SimpleBindingListAdapter<SettingItemViewModel>(R.layout.layout_my_page_setting) {

    override fun createViewHolder(view: View): BaseBindingViewHolder<SettingItemViewModel, ViewDataBinding> {
        return SettingItemViewHolder(view) as BaseBindingViewHolder<SettingItemViewModel, ViewDataBinding>
    }
}
