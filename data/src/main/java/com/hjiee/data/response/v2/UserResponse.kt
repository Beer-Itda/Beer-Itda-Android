package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("review_count")
    val reviewCount: Int?,
    @SerializedName("level_id")
    val levelId: Int?
) {
}