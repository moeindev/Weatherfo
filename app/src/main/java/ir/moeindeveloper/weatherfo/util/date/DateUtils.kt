package ir.moeindeveloper.weatherfo.util.date

import java.util.*

enum class DateEvents {
    DAWN,
    MORNING,
    NOON,
    EVENING,
    NIGHT
}


fun getDateEvent(): DateEvents {
    val cal = Calendar.getInstance()
    return when(cal.get(Calendar.HOUR_OF_DAY)) {
        in 0..6 -> DateEvents.DAWN
        in 7..11 -> DateEvents.MORNING
        in 12..16 -> DateEvents.NOON
        in 17..19 -> DateEvents.EVENING
        in 20..23 -> DateEvents.NIGHT
        else -> DateEvents.DAWN
    }
}


