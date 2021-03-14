package com.ddd4.synesthesia.beer.util.provider

import android.content.Context
import com.ddd4.synesthesia.beer.R
import javax.inject.Inject

open class StringProvider @Inject constructor(private val context: Context) :
    IStringResourceProvider, INoticeStringResourceProvider {
    override fun getError() = getStringRes(R.string.error_message)
    override fun getStringRes(resId: Int): String = context.getString(resId)
}