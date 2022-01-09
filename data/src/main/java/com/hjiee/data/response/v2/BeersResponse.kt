package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class BeersResponse(
    @SerializedName("beer")
    val beers: List<BeerResponse>?,
    @SerializedName("cursor")
    val cursor: CursorResponse?
)