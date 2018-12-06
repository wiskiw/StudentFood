package by.wiskiw.studentfood.utils

import java.util.*

object RecipeTimeUtil {

    private const val LONG_TIME_FORMAT = "HH:mm"

    fun formatToString(timeMs: Long): String {
        val calendar = GregorianCalendar()
        calendar.timeInMillis = timeMs
        return if (calendar.get(GregorianCalendar.HOUR_OF_DAY) > 2) {
            // время по формату LONG_TIME_FORMAT
            DateUtils.format(timeMs, LONG_TIME_FORMAT)
        } else {
            calendar.get(GregorianCalendar.MINUTE).toString() + " min"
        }
    }
}