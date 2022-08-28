package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("is_first_signup_user")
    val isFirstSignupUser: Boolean?
)