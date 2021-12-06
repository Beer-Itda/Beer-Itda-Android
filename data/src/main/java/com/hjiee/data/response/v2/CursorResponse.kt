package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class CursorResponse(
    @SerializedName("cursor")
    val cursor: Int?
)