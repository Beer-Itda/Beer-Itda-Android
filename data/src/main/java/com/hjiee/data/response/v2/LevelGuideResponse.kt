package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class LevelGuideResponse(
    @SerializedName("review_level")
    val reviewLevel: MyLevelResponse,
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
    @SerializedName("current_level_id")
    val currentLevelId: Int?,
    @SerializedName("current_level_image")
    val currentLevelImage: String?,
    @SerializedName("current_review_count")
    val currentReviewCount: Int?,
    @SerializedName("current_level")
    val currentLevel: String?,
    @SerializedName("need_review_count")
    val needReviewCount: Int?,
    @SerializedName("next_level")
    val nextLevel: String?,
)