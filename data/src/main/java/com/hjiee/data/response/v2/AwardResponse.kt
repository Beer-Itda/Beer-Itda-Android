package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class AwardResponse(
    @SerializedName("beer")
    val beer: BeerResponse?
)