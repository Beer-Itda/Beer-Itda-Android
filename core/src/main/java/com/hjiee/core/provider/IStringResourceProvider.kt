package com.hjiee.core.provider

import androidx.annotation.StringRes

interface IStringResourceProvider {
    fun getStringRes(@StringRes resId: Int): String
}