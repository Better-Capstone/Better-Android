package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("com/ssu/better/entity/user")
    val user: User,
)
