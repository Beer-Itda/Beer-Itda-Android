package com.hjiee.domain.entity.request

import com.google.gson.annotations.SerializedName

class RequestSelectedAroma(
    @SerializedName("aroma_ids")
    val aromaIds: IntArray
) {
}