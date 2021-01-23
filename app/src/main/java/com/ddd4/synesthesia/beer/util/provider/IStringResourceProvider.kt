package com.ddd4.synesthesia.beer.util.provider

import androidx.annotation.StringRes

interface IStringResourceProvider {
    fun getError() : String
    fun getStringRes(@StringRes resId : Int) : String
}