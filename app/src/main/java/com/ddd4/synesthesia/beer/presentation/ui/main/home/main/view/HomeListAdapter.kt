package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindableAdapter
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.HomeViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.IHomeItemViewModel

class HomeListAdapter : BaseBindableAdapter<HomeViewType, IHomeItemViewModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<IHomeItemViewModel, ViewDataBinding> {
        return HomeViewHolder.getViewHolder(parent, HomeViewType.values()[viewType])
    }
}

enum class HomeViewType {
    AWARD,
    RECOMMEND
}

enum class HomeBeerRecommendType {
    STYLE,
    AROMA,
    RANDOM,
}