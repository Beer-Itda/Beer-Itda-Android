package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class LevelGuideResponse(
    @SerializedName("user")
    val user: MyLevelResponse,
    @SerializedName("levels")
    val levels: List<LevelResponse>?
)

data class LevelResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("level_image")
    val levelImage: String?,
    @SerializedName("level_count")
    val levelCount: Int?
)

data class MyLevelResponse(
    @SerializedName("level")
    val level: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("nextLevel")
    val nextLevel: String,
) {

}