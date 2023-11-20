package com.ssu.better.entity.user

data class UserPref(
    val id: Long,
    val nickname: String,
    val rank: Int = 0,
    val score: Int = 0,
)
