package com.ddd4.synesthesia.beer.data.model.filter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ale(
    @SerializedName("Ale")
    val ale: List<String>?,
    @SerializedName("Dark Beer")
    val darkBeer: List<String>?,
    @SerializedName("IPA")
    val ipa: List<String>?,
    @SerializedName("Wheat Beer")
    val wheatBeer: List<String>?
) : Parcelable