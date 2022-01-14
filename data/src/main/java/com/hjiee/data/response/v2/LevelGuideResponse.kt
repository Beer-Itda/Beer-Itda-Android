package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class LevelGuideResponse(
    @SerializedName("levels")
    val levels: List<LevelResponse>?
)