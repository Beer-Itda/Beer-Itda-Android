package com.ddd4.synesthesia.beer.presentation.ui.common.filter

import android.content.Context
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.viewmodel.AromaViewModel.Companion.MAX_AROMA_COUNT
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.viewmodel.StyleViewModel.Companion.MAX_STYLE_COUNT
import com.hjiee.core.provider.IStringResourceProvider
import javax.inject.Inject

class FilterStringProvider @Inject constructor(
    private val context: Context
): IStringResourceProvider {

    enum class Code {
        MAX_STYLE,
        MAX_AROMA,
    }

    fun getString(type: Code): String = when (type) {
        Code.MAX_STYLE -> {
            String.format(
                getStringRes(R.string.max_choice_count_style),
                MAX_STYLE_COUNT
            )
        }
        Code.MAX_AROMA -> {
            String.format(
                getStringRes(R.string.max_choice_count_aroma),
                MAX_AROMA_COUNT
            )
        }
    }

    fun getErrorMessage() = getStringRes(R.string.error_message)

    override fun getStringRes(resId: Int): String {
        return context.getString(resId)
    }
}