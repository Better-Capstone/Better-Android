package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

enum class StudyPeriod {
    @SerializedName("EVERYDAY")
    EVERYDAY,

    @SerializedName("WEEKLY")
    WEEKLY,

    @SerializedName("BIWEEKLY")
    BIWEEKLY,
}
