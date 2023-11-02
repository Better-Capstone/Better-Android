package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName

data class UserRank(
    @SerializedName("score")
    val score: Int,

    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)
