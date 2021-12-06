package com.hjiee.core.provider

import android.content.Context
import com.hjiee.core.R
import javax.inject.Inject

open class StringProvider @Inject constructor(private val context: Context) :
    IStringResourceProvider, INoticeStringResourceProvider {
    override fun getError() = getStringRes(R.string.error_message)
    override fun getStringRes(resId: Int): String = context.getString(resId)
}