package com.ddd4.synesthesia.beer.presentation.ui.filter.style.view

import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.viewmodel.StyleViewModel.Companion.MAX_STYLE_COUNT
import com.ddd4.synesthesia.beer.util.provider.StringProvider
import javax.inject.Inject

class StyleStringProvider @Inject constructor(
    private val stringProvider: StringProvider
) {

    enum class Code {
        MAX,
    }

    fun getStringRes(type: Code): String = when (type) {
        Code.MAX -> {
            String.format(
                stringProvider.getStringRes(R.string.max_choice_count_style),
                MAX_STYLE_COUNT
            )
        }
    }
}