package com.ssu.better.util

import com.ssu.better.R
import com.ssu.better.entity.study.Category
import com.ssu.better.entity.study.StudyCategory

fun getCategoryIcon(category: Category): Int {
    return when (category) {
        Category.CERTIFICATE -> R.drawable.ic_category_certificate
        Category.HEALTH -> R.drawable.ic_category_health
        Category.LANGUAGE -> R.drawable.ic_category_language
        Category.ART -> R.drawable.ic_category_art
        Category.BUSINESS -> R.drawable.ic_category_business
        Category.IT -> R.drawable.ic_category_it
        else -> R.drawable.ic_better_logo
    }
}

fun StudyCategory.getIcon(): Int {
    return try {
        when (Category.valueOf(this.name)) {
            Category.CERTIFICATE -> R.drawable.ic_category_certificate
            Category.HEALTH -> R.drawable.ic_category_health
            Category.LANGUAGE -> R.drawable.ic_category_language
            Category.ART -> R.drawable.ic_category_art
            Category.BUSINESS -> R.drawable.ic_category_business
            Category.IT -> R.drawable.ic_category_it
            else -> R.drawable.ic_better_logo
        }
    } catch (e: Exception) {
        R.drawable.ic_better_logo
    }
}
