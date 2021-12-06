package com.hjiee.core.provider

import androidx.annotation.StringRes

interface IStringResourceProvider {
    fun getError(): String
    fun getStringRes(@StringRes resId: Int): String
}