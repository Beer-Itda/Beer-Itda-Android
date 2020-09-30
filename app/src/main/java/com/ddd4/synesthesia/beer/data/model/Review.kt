package com.ddd4.synesthesia.beer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    @SerializedName("ratio")
    val ratio: Float?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("beer")
    val beer: Beer?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("nickname")
    val nickname: String?
) : Parcelable