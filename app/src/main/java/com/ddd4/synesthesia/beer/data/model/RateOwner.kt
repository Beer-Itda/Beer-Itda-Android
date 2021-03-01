package com.ddd4.synesthesia.beer.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateOwner(
    @SerializedName("beer_id")
    val beerId: Int,
    @SerializedName("ratio")
    val ratio: Float,
    @SerializedName("user_id")
    val userId: Int
) : Parcelable