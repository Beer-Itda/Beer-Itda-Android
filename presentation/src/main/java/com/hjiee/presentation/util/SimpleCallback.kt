package com.hjiee.presentation.util

interface SimpleCallback {
    companion object {
        var callback: SimpleCallback? = null
    }

    fun call(text: String)
}