package com.hjiee.data.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName(value = "id")
    val id: Int
)