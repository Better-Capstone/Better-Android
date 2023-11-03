package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("nickname")
    val nickname: String,
)
