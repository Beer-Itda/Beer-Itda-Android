package com.ddd4.synesthesia.beer.data.model.filter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Etc(
    @SerializedName("etc")
    val etc: List<String>
) : Parcelable