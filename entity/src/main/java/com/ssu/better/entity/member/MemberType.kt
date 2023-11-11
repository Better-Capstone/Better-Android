package com.ssu.better.entity.member

import com.google.gson.annotations.SerializedName

enum class MemberType {
    @SerializedName("OWNER")
    OWNER,

    @SerializedName("MEMBER")
    MEMBER,

    @SerializedName("WITHDRAW")
    WITHDRAW,
}
