package ir.moeindeveloper.weatherfo.data.preference

import android.content.SharedPreferences
import ir.moeindeveloper.weatherfo.data.Constants
import ir.moeindeveloper.weatherfo.util.preference.getDouble
import javax.inject.Inject

class AppSettings @Inject constructor(private val preferences: SharedPreferences) {

    fun saveCity(name: String, lat: Double, lon: Double) {
        save()
        saveName(name)
        saveLatitude(lat)
        saveLongitude(lon)
    }

    private fun saveName(name: String) =
        preferences.edit().putString(Constants.PreferenceKey.name,name).apply()

    private fun saveLatitude(lat: Double) =
        preferences.edit().putFloat(Constants.PreferenceKey.lat,lat.toFloat()).apply()

    private fun saveLongitude(lon: Double) =
        preferences.edit().putFloat(Constants.PreferenceKey.lon,lon.toFloat()).apply()

    private fun save() = preferences.edit().putBoolean(Constants.PreferenceKey.isSaved,true).apply()

    fun isSaved(): Boolean = preferences.getBoolean(Constants.PreferenceKey.isSaved,false)

    fun getName(): String? = preferences.getString(Constants.PreferenceKey.name,null)

    fun getLat(): Double? = preferences.getDouble(Constants.PreferenceKey.lat,
        Constants.DefaultLocation.lat)

    fun getLon(): Double? = preferences.getDouble(Constants.PreferenceKey.lon,
        Constants.DefaultLocation.lon)
}