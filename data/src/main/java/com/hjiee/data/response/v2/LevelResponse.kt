package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class LevelResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("level_count")
    val levelCount: Int?
)