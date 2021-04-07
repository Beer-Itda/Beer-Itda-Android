package com.ddd4.synesthesia.beer.presentation.ui.common.filter

import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.viewmodel.AromaViewModel.Companion.MAX_AROMA_COUNT
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.viewmodel.StyleViewModel.Companion.MAX_STYLE_COUNT
import com.ddd4.synesthesia.beer.util.provider.StringProvider
import javax.inject.Inject

class FliterStringProvider @Inject constructor(
    private val stringProvider: StringProvider
) {

    enum class Code {
        MAX_STYLE,
        MAX_AROMA,
    }

    fun getStringRes(type: Code): String = when (type) {
        Code.MAX_STYLE -> {
            String.format(
                stringProvider.getStringRes(R.string.max_choice_count_style),
                MAX_STYLE_COUNT
            )
        }
        Code.MAX_AROMA -> {
            String.format(
                stringProvider.getStringRes(R.string.max_choice_count_aroma),
                MAX_AROMA_COUNT
            )
        }
    }
}