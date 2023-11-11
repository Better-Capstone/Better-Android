package com.ssu.better.entity.member

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("id")
    val memberId: Long,

    @SerializedName("kickCount")
    val kickCount: Int,

    @SerializedName("memberType")
    val memberType: MemberType,

    @SerializedName("notifyTime")
    val notifyTime: String,
)
