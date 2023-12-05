package com.ssu.better.entity.challenge

import com.google.gson.annotations.SerializedName

data class Challenge(
    @SerializedName("id")
    val id: Long,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("approveMember")
    val approveMember: ArrayList<Long>,

    @SerializedName("rejectMember")
    val rejectMember: ArrayList<Long>,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
)
