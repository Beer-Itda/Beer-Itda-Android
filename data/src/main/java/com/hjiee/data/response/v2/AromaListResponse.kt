package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

class AromaListResponse(
    @SerializedName("aromaList")
    val aromaList: List<AromaResponse>?
)

class AromaResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("aroma")
    val name: String?
)