package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName

data class UserRank(
    @SerializedName("score")
    val score: Int,

    @SerializedName("user")
    val user: User,

    @SerializedName("id")
    val id: Long,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("userRankHistoryList")
    val userRankHistoryList: ArrayList<UserRankHistory>,
)
