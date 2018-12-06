package by.wiskiw.studentfood.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun format(timeMs: Long, pattern: String) = format(Date(timeMs), pattern)

    fun format(date: Date, pattern: String): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(date)
    }
}