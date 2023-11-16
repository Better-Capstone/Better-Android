package com.ssu.better.util

import java.text.ParseException
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun YearMonth.dateFormat(pattern: String, locale: Locale = Locale.KOREA): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

fun LocalDate.dateFormat(pattern: String, locale: Locale = Locale.KOREA): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

fun LocalTime.timeFormat(pattern: String, locale: Locale = Locale.KOREA): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

fun getDaysOfWeek(localDate: LocalDate, pattern: String): ArrayList<LocalDate> {
    val df = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)

    val list = arrayListOf<LocalDate>()
    try {
        val systemTimeZone = ZoneId.systemDefault()
        val zonedDateTime = localDate.atStartOfDay(systemTimeZone)
        val date: Date = Date.from(zonedDateTime.toInstant())
        val cal: Calendar = Calendar.getInstance()

        cal.time = date

        for (i in 1..7) {
            cal.set(Calendar.DAY_OF_WEEK, i)
            val day = convertToLocalDateViaInstant(cal.time)
            if (day != null)list.add(day)
        }
    } catch (e: ParseException) {
    }
    return list
}

fun convertToLocalDateViaInstant(dateToConvert: Date): LocalDate? {
    return dateToConvert.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun convertToDateViaInstant(dateToConvert: LocalDate): Date? {
    return Date.from(
        dateToConvert.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant(),
    )
}
