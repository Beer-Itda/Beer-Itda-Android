package com.ddd4.synesthesia.beer.data.model.filter.style

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StyleMiddleCategories(
    @SerializedName("mid_name")
    val middleName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("small_categories")
    val smallCategories: List<String>?
) : Parcelable