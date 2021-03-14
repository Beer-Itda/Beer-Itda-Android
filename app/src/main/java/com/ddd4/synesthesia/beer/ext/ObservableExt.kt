package com.ddd4.synesthesia.beer.ext

import androidx.databinding.ObservableField

object ObservableExt {
    fun ObservableString(str: String? = null) = ObservableField<String>(str)
}
