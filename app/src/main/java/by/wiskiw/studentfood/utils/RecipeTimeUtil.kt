package by.wiskiw.studentfood.utils

import java.util.*

object RecipeTimeUtil {

    private const val LONG_TIME_FORMAT = "HH:mm"

    fun formatToString(timeMs: Long): String {

        val calendar = GregorianCalendar()
        calendar.timeInMillis = timeMs
        // todo fix It
        /*
        return if (TimeUnit.MILLISECONDS.toHours(timeMs) > 2) {
            // время по формату LONG_TIME_FORMAT
            DateUtils.formatData(timeMs, LONG_TIME_FORMAT)
        } else {
            calendar.get(GregorianCalendar.MINUTE).toString() + " min"
        }
        */
        return calendar.get(GregorianCalendar.MINUTE).toString() + " min"

    }
}