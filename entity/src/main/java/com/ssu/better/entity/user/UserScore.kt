package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName

data class UserScore(

    @SerializedName("id")
    val userId: Long,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("score")
    val score: Long,
)
