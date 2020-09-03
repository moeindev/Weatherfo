package ir.moeindeveloper.weatherfo.util.date

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

enum class DateEvents {
    DAWN,
    MORNING,
    NOON,
    EVENING,
    NIGHT
}


fun getDateEvent(date: Date? = null): DateEvents {
    val cal = Calendar.getInstance()
    if (date != null) cal.time = date
    return when(cal.get(Calendar.HOUR_OF_DAY)) {
        in 0..6 -> DateEvents.DAWN
        in 7..11 -> DateEvents.MORNING
        in 12..16 -> DateEvents.NOON
        in 17..19 -> DateEvents.EVENING
        in 20..23 -> DateEvents.NIGHT
        else -> DateEvents.DAWN
    }
}


fun Int.toHour(): String {
    return try {
        val dtf = SimpleDateFormat("hh a")
        val date = Date((this * 1000).toLong())
        dtf.format(date)
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}

fun Int.toDayName(): String {
    return try {
        val dtf = SimpleDateFormat("EEEE")
        val date = Date((this * 1000).toLong())
        dtf.format(date)
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}


fun Int.toDate(): Date? {
    return try {
        Date((this * 1000).toLong())
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}