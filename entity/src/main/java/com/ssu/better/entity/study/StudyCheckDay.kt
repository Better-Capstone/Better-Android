package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName
import java.time.DayOfWeek

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

fun StudyCheckDay.toDayOfWeek(): DayOfWeek? {
    return when (this) {
        StudyCheckDay.MON -> DayOfWeek.MONDAY
        StudyCheckDay.TUE -> DayOfWeek.TUESDAY
        StudyCheckDay.WED -> DayOfWeek.WEDNESDAY
        StudyCheckDay.THU -> DayOfWeek.THURSDAY
        StudyCheckDay.FRI -> DayOfWeek.FRIDAY
        StudyCheckDay.SAT -> DayOfWeek.SATURDAY
        StudyCheckDay.SUN -> DayOfWeek.SUNDAY
        else -> null
    }
}
