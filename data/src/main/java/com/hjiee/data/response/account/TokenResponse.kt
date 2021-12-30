package com.hjiee.data.response.account

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName(value = "token_type")
    val tokenType: String?,
    @SerializedName(value = "access_token")
    val accessToken: String?,
    @SerializedName(value = "expires_in")
    val expiresIn: Int?,
    @SerializedName(value = "refresh_token")
    val refreshToken: String?,
    @SerializedName(value = "refresh_token_expires_in")
    val refreshTokenExpiresIn: Int?,
    @SerializedName(value = "scope")
    val scope: String?
)