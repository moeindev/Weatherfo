package ir.moeindeveloper.weatherfo.data.preference

import android.content.SharedPreferences
import ir.moeindeveloper.weatherfo.data.Constants
import ir.moeindeveloper.weatherfo.util.preference.getDouble
import ir.moeindeveloper.weatherfo.util.preference.putDouble
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
        preferences.edit().putDouble(Constants.PreferenceKey.lat,lat)

    private fun saveLongitude(lon: Double) =
        preferences.edit().putDouble(Constants.PreferenceKey.lon,lon)

    private fun save() = preferences.edit().putBoolean(Constants.PreferenceKey.isSaved,true).apply()

    fun isSaved(): Boolean = preferences.getBoolean(Constants.PreferenceKey.isSaved,false)

    fun getName(): String? = preferences.getString(Constants.PreferenceKey.name,Constants.DefaultLocation.name)

    fun getLat(): Double? = preferences.getDouble(Constants.PreferenceKey.lat,
        Constants.DefaultLocation.lat)

    fun getLon(): Double? = preferences.getDouble(Constants.PreferenceKey.lon,
        Constants.DefaultLocation.lon)
}