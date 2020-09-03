package ir.moeindeveloper.weatherfo.util.weather

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import ir.moeindeveloper.weatherfo.R
import ir.moeindeveloper.weatherfo.data.model.FeelsLike
import ir.moeindeveloper.weatherfo.data.model.Temp
import ir.moeindeveloper.weatherfo.util.date.DateEvents
import ir.moeindeveloper.weatherfo.util.date.getDateEvent
import java.util.*

fun Double.toStringTemp(other: Double? = null): SpannableString {
    if (other == null) return SpannableString("${this.toInt()}°")

    val valueOne = this.toStringTemp()
    val valueTwo = other.toStringTemp()


    val spannableString = SpannableString("$valueOne/$valueTwo")
    spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FFFFFF")),0,valueOne.length-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#99FFFFFF")), valueOne.length,((valueOne.length + valueTwo.length)),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return spannableString
}


fun getCurrentTemp(temp: Temp, feelsLike: FeelsLike): SpannableString {
    return when(getDateEvent()){
        DateEvents.DAWN -> {
            temp.morn.toStringTemp(feelsLike.morn)
        }

        DateEvents.MORNING -> {
            temp.morn.toStringTemp(feelsLike.morn)
        }

        DateEvents.NOON -> {
            temp.eve.toStringTemp(feelsLike.eve)
        }

        DateEvents.EVENING -> {
            temp.eve.toStringTemp(feelsLike.eve)
        }

        DateEvents.NIGHT -> {
            temp.night.toStringTemp(feelsLike.night)
        }
    }
}


fun String.getWeatherIcon(date: Date? = null): Int {
    return when(this) {
        "01d" -> {
            //Clear sky
            when(getDateEvent(date)){
                DateEvents.DAWN -> R.drawable.ic_weather_clear_sky_dawn

                DateEvents.MORNING -> R.drawable.ic_weather_clear_sky_morning

                DateEvents.NOON -> R.drawable.ic_weather_clear_sky_morning

                DateEvents.EVENING -> R.drawable.ic_weather_clear_sky_evening

                DateEvents.NIGHT -> R.drawable.ic_weather_clear_sky_night

            }
        }

        "01n" -> {
            //Clear sky
            when(getDateEvent(date)){
                DateEvents.DAWN -> R.drawable.ic_weather_clear_sky_dawn

                DateEvents.MORNING -> R.drawable.ic_weather_clear_sky_morning

                DateEvents.NOON -> R.drawable.ic_weather_clear_sky_morning

                DateEvents.EVENING -> R.drawable.ic_weather_clear_sky_evening

                DateEvents.NIGHT -> R.drawable.ic_weather_clear_sky_night

            }
        }

        "02d" -> {
            //Few clouds
            when(getDateEvent(date)){
                DateEvents.DAWN -> R.drawable.ic_weather_few_cloud_dawn

                DateEvents.MORNING -> R.drawable.ic_weather_few_cloud_morning

                DateEvents.NOON -> R.drawable.ic_weather_few_cloud_morning

                DateEvents.EVENING -> R.drawable.ic_weather_few_cloud_evening

                DateEvents.NIGHT -> R.drawable.ic_weather_few_clound_night

            }
        }

        "02n" -> {
            //Few clouds
            when(getDateEvent(date)){
                DateEvents.DAWN -> R.drawable.ic_weather_few_cloud_dawn

                DateEvents.MORNING -> R.drawable.ic_weather_few_cloud_morning

                DateEvents.NOON -> R.drawable.ic_weather_few_cloud_morning

                DateEvents.EVENING -> R.drawable.ic_weather_few_cloud_evening

                DateEvents.NIGHT -> R.drawable.ic_weather_few_clound_night

            }
        }

        "03d" -> {
            //scattered clouds
            R.drawable.ic_weather_scattered_clouds
        }

        "03n" -> {
            //scattered clouds
            R.drawable.ic_weather_scattered_clouds
        }


        "04d" -> {
            //broken clouds
            R.drawable.ic_weather_broken_cloud
        }

        "04n" -> R.drawable.ic_weather_broken_cloud

        "09d" -> {
            //showar rain
            R.drawable.ic_weather_shower_raint
        }


        "09n" -> {
            //showar rain
            R.drawable.ic_weather_shower_raint
        }

        "10d" -> {
            //rain
            R.drawable.ic_weather_rain
        }

        "10n" -> {
            R.drawable.ic_weather_rain
        }

        "11d" -> {
            //thunderstorm
            R.drawable.ic_weather_thunderstorm
        }

        "11n" -> {
            //thunderstorm
            R.drawable.ic_weather_thunderstorm
        }

        "13d" -> {
            //snow
            R.drawable.ic_weather_snow
        }

        "13n" -> {
            //snow
            R.drawable.ic_weather_snow
        }


        "50d" -> {
            //mist
            R.drawable.ic_weather_mist
        }

        "50n" -> {
            //mist
            R.drawable.ic_weather_mist
        }

        else -> {
            Log.d("icon","illegal")
            Log.d("iconFromServer",this)
            R.drawable.ic_weather_mist
        }
    }
}