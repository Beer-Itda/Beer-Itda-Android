package com.ddd4.synesthesia.beer.data.model.filter.style

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StyleLargeCategories(
    @SerializedName("big_name")
    val bigName: String?,
    @SerializedName("mid_categories")
    val styleMiddleCategories: List<StyleMiddleCategories>?
) : Parcelable