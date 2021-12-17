package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("beer_id")
    val beerId: Int?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val reviewId: Int?,
    @SerializedName("star")
    val star: Float?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: String?
)