package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

data class GroupRankHistory(
    @SerializedName("id")
    val groupRankHistoryId: Long,

    @SerializedName("score")
    val score: Int,

    @SerializedName("totalNumber")
    val totalNumber: Int,

    @SerializedName("participantsNumber")
    val participantsNumber: Int,
)
