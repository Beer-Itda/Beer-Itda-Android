package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.view

import android.view.View
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item.SettingItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item.SettingItemViewModel

class SettingAdapter : SimpleBindingListAdapter<SettingItemViewModel>(R.layout.layout_my_page_setting) {

    override fun createViewHolder(view: View): BaseBindingViewHolder<SettingItemViewModel, ViewDataBinding> {
        return SettingItemViewHolder(view) as BaseBindingViewHolder<SettingItemViewModel, ViewDataBinding>
    }
}
