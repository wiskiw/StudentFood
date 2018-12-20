package by.wiskiw.studentfood.utils

import java.text.SimpleDateFormat
import java.util.*

@Deprecated("конвертирует только дату, но не время")
object DateUtils {

    fun formatData(dateMs: Long, pattern: String) = formatData(Date(dateMs), pattern)

    fun formatData(date: Date, pattern: String): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(date)
    }
}