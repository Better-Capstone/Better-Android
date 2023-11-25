package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserRankHistory

data class Study(
    @SerializedName("id")
    val studyId: Long,

    @SerializedName("owner")
    val owner: User,

    @SerializedName("category")
    val category: StudyCategory,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("status")
    val status: StudyStatus,

    @SerializedName("period")
    val period: StudyPeriod,

    @SerializedName("checkDay")
    val checkDay: StudyCheckDay,

    @SerializedName("numOfMember")
    val memberCount: Int,

    @SerializedName("kickCondition")
    val kickCondition: Int,

    @SerializedName("maximumCount")
    val maximumCount: Int,

    @SerializedName("minRank")
    val minRank: Int,

    @SerializedName("memberList")
    val memberList: ArrayList<Member>,

    @SerializedName("taskList")
    val taskList: ArrayList<Task>?,

    @SerializedName("userRankHistoryList")
    val userRankHistoryList: ArrayList<UserRankHistory>,

    @SerializedName("groupRank")
    val groupRank: GroupRank,
)
