package ir.moeindeveloper.weatherfo.data.local

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.moeindeveloper.weatherfo.data.model.Cities
import java.io.IOException
import javax.inject.Inject

class CityListProvider @Inject constructor(@ApplicationContext private val context: Context) {

    val cities: Cities by lazy {
        var cityJsonString = ""

        try {
            val inputStream = context.assets.open("cities.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            cityJsonString = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return@lazy Gson().fromJson(cityJsonString,Cities::class.java)
    }
}