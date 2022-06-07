package com.hjiee.presentation.ui.review.view

import android.content.Context
import com.hjiee.presentation.R
import com.hjiee.core.provider.IStringResourceProvider
import javax.inject.Inject

class ReviewStringProvider @Inject constructor(
    private val context: Context
) : IStringResourceProvider {

    fun getTitle(reviewCount: Int): String = if (reviewCount == 0) {
        getStringRes(R.string.review)
    } else {
        context.getString(R.string.review_with_count, reviewCount.toFloat())
    }

    override fun getStringRes(resId: Int): String {
        return context.getString(resId)
    }
}