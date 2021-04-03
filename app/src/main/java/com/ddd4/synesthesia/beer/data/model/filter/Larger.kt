package com.ddd4.synesthesia.beer.data.model.filter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Larger(
    @SerializedName("Bock")
    val bock: List<String>,
    @SerializedName("Larger")
    val larger: List<String>
) : Parcelable