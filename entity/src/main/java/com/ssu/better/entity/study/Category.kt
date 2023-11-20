package com.ssu.better.entity.study

enum class Category(val kor: String, val id: Long) {
    IT("IT", 1),
    LANGUAGE("언어", 2),
    ART("예체능", 3),
    BUSINESS("비즈니스", 4),
    CERTIFICATE("자격증", 5),
    HEALTH("건강", 6),
    ALL("전체", 7)
}
