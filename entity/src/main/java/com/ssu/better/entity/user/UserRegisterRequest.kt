package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName

data class UserRegisterRequest(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("nickname")
    val nickName: String,
)
