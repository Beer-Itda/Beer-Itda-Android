package com.hjiee.data.response.common

import com.google.gson.annotations.SerializedName

abstract class PageResponse(
    @SerializedName("total_page")
    val totalPage: Int? = null,
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("previous_page")
    val previousPage: Int? = null,
    @SerializedName("next_page")
    val nextPage: Int? = null
) {
}