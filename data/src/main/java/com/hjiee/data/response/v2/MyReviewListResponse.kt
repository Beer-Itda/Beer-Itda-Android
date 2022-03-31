package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class MyReviewItemResponse(
    @SerializedName("beer")
    val beer: BeerResponse?,
    @SerializedName("review")
    val review: ReviewResponse?
)