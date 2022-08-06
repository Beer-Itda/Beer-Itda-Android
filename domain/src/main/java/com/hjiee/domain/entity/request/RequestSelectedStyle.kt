package com.hjiee.domain.entity.request

import com.google.gson.annotations.SerializedName

class RequestSelectedStyle(
    @SerializedName("style_ids")
    val styleIds: IntArray
) {
}