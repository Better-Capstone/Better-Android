package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

enum class StudyCheckDay {
    @SerializedName("EVERYDAY")
    EVERYDAY,

    @SerializedName("MON")
    MON,

    @SerializedName("TUE")
    TUE,

    @SerializedName("WED")
    WED,

    @SerializedName("THU")
    THU,

    @SerializedName("FRI")
    FRI,

    @SerializedName("SAT")
    SAT,

    @SerializedName("SUN")
    SUN,
}
