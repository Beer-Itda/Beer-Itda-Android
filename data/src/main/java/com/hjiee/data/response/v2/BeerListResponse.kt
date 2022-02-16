package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class BeerListResponse(
    @SerializedName("total_data")
    val totalData: Int,
    @SerializedName("data")
    val beers: List<BeerResponse>?
)