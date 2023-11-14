package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

data class GroupRank(
    @SerializedName("id")
    val groupRankId: Long,

    @SerializedName("score")
    val score: Int,
)
