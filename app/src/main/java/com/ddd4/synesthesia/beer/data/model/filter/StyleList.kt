package com.ddd4.synesthesia.beer.data.model.filter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StyleList(
    @SerializedName("Ale")
    val ale: Ale?,
    @SerializedName("Lambic")
    val lambic: Lambic?,
    @SerializedName("Larger")
    val larger: Larger?,
    @SerializedName("etc")
    val etc: Etc?
) : Parcelable