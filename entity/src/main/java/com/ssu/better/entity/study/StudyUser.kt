package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.user.UserRank

data class StudyUser(
    @SerializedName("id")
    val userId: Long,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("userRank")
    val userRank: UserRank,

    @SerializedName("memberList")
    val memberList: ArrayList<Member>,

    @SerializedName("ownerStudyList")
    val ownerStudyList: ArrayList<Study>,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
)
