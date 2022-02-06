package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName
import com.hjiee.data.response.common.PageResponse

data class BeerListResponse(
    @SerializedName("total_data")
    val totalData: Int,
    @SerializedName("beer_data")
    val beers: List<BeerResponse>?
) : PageResponse()