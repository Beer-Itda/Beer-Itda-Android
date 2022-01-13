package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName
import com.hjiee.data.response.common.PageResponse

data class SearchResponse(
    @SerializedName("total_data")
    val totalCount: Int? = null,
    @SerializedName("beer_data")
    val beers: List<BeerResponse>? = null
) : PageResponse()