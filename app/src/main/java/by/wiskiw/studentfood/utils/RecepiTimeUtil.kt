package by.wiskiw.studentfood.utils

import java.util.*

object RecipeTimeUtil {

    private const val SHORT_TIME_FORMAT = ""
    private const val LONG_TIME_FORMAT = ""

    fun formatToString(timeMs: Long): String {
        val calendar = GregorianCalendar()
        calendar.timeInMillis = timeMs
        return if (calendar.get(GregorianCalendar.HOUR_OF_DAY) > 2) {
            // время по формату LONG_TIME_FORMAT
            DateUtils.format(timeMs, SHORT_TIME_FORMAT)
        } else {
            // todo время по формату SHORT_TIME_FORMAT
            // DateUtils.format(timeMs, LONG_TIME_FORMAT)
            calendar.get(GregorianCalendar.MINUTE).toString() + " min"
        }
    }
}