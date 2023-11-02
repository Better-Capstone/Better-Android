package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
    @SerializedName("kakaoToken")
    val token: String,
)
