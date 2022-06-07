package com.hjiee.presentation.util.ext

import androidx.databinding.ObservableField

object ObservableExt {
    fun ObservableString(str: String? = null) = ObservableField<String>(str)
}
