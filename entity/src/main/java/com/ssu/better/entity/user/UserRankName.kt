package com.ssu.better.entity.user

enum class UserRankName(val limit: Int, val ko: String) {
    OFF_CANDLE(0, "꺼진 촛불"),
    CANDLE(4000, "촛불"),
    FIRE(6000, "불꽃"),
    BONFIRE(8000, "모닥불"),
}
