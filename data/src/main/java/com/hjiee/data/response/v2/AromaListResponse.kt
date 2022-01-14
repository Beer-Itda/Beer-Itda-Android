package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class AromaListResponse(
    @SerializedName("aromaList")
    val aromaList: List<AromaResponse>?
)

data class AromaResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("aroma")
    val name: String?
)