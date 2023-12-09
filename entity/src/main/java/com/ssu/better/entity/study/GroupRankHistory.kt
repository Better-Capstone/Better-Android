package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.challenge.ChallengeHistory
import com.ssu.better.entity.task.TaskGroup

data class GroupRankHistory(
    @SerializedName("id")
    val groupRankHistoryId: Long,

    @SerializedName("score")
    val score: Int,

    @SerializedName("totalNumber")
    val totalNumber: Int,

    @SerializedName("participantsNumber")
    val participantsNumber: Int,

    @SerializedName("groupRank")
    val groupRank: GroupRank,

    @SerializedName("taskGroup")
    val taskGroup: TaskGroup,

    @SerializedName("challengeList")
    val challengeList: ArrayList<ChallengeHistory>,

    @SerializedName("createdAt")
    val createdAt: String,

)
