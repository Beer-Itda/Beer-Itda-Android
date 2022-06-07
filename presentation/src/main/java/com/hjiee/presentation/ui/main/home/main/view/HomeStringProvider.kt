package com.hjiee.presentation.ui.main.home.main.view

import com.hjiee.presentation.R
import com.hjiee.core.provider.StringProvider
import javax.inject.Inject

class HomeStringProvider @Inject constructor(
    private val stringProvider: StringProvider
) {



    fun getStringRes(type: HomeBeerRecommendType): String = when (type) {
        HomeBeerRecommendType.STYLE -> {
            stringProvider.getStringRes(R.string.title_like_style)
        }
        HomeBeerRecommendType.AROMA -> {
            stringProvider.getStringRes(R.string.title_like_aroma)
        }
        HomeBeerRecommendType.RANDOM -> {
            stringProvider.getStringRes(R.string.title_like_random)
        }
    }
}