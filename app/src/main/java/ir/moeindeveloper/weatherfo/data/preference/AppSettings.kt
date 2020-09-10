package ir.moeindeveloper.weatherfo.data.preference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.moeindeveloper.weatherfo.R
import ir.moeindeveloper.weatherfo.data.Constants
import ir.moeindeveloper.weatherfo.data.Constants.PreferenceKey.LanguageManager.cmLangIsSelected
import ir.moeindeveloper.weatherfo.data.Constants.PreferenceKey.LanguageManager.cmLangKey
import ir.moeindeveloper.weatherfo.util.preference.getDouble
import ir.moeindeveloper.weatherfo.util.preference.putDouble
import javax.inject.Inject

class AppSettings @Inject constructor(@ApplicationContext private val context: Context, private val preferences: SharedPreferences) {

    fun saveCity(name: String,faName: String, lat: Double, lon: Double) {
        save()
        saveName(name)
        saveLatitude(lat)
        saveLongitude(lon)
    }

    private fun saveName(name: String) =
        preferences.edit().putString(Constants.PreferenceKey.name,name).apply()

    private fun saveFaName(faName: String) =
        preferences.edit().putString(Constants.PreferenceKey.faName,faName).apply()

    private fun saveLatitude(lat: Double) =
        preferences.edit().putDouble(Constants.PreferenceKey.lat,lat)

    private fun saveLongitude(lon: Double) =
        preferences.edit().putDouble(Constants.PreferenceKey.lon,lon)

    private fun save() = preferences.edit().putBoolean(Constants.PreferenceKey.isSaved,true).apply()

    fun isSaved(): Boolean = preferences.getBoolean(Constants.PreferenceKey.isSaved,false)

    private fun getName(): String? = preferences.getString(Constants.PreferenceKey.name,Constants.DefaultLocation.name)

    private fun getFaName(): String? = preferences.getString(Constants.PreferenceKey.faName,Constants.DefaultLocation.faName)

    fun getLocaleName(): String? {
        return when(getLanguage()){
            "fa" -> getFaName()
            "en" -> getName()
            else -> getName()
        }
    }

    fun getLat(): Double? = preferences.getDouble(Constants.PreferenceKey.lat,
        Constants.DefaultLocation.lat)

    fun getLon(): Double? = preferences.getDouble(Constants.PreferenceKey.lon,
        Constants.DefaultLocation.lon)


    enum class Languages(val language: String, val langName: Int) {
        ENGLISH("en", R.string.lang_en),PERSIAN("fa",R.string.lang_fa)
    }

    fun initLanguage(application: Application) {
        Lingver.init(application,getLanguage())
        if (!isSelected()) {
            val lang = when(getLanguage()){
                "fa" -> Languages.PERSIAN
                "en" -> Languages.ENGLISH
                else -> Languages.ENGLISH
            }
            saveLanguage(lang)
        }
    }

    /**
     * Init Keys:
     */
    // *********************************************

    // *********************************************

    /**
     * Save selected language:
     */
    fun saveLanguage(languages: Languages){
        saveLang(languages.language)
        saveIsSelected()
        Lingver.getInstance().setLocale(context,languages.language)
    }


    /*
    save language string
     */
    private fun saveLang(language: String) =
        preferences.edit().putString(cmLangKey,language).apply()

    /*
    save if selected as boolean
     */
    private fun saveIsSelected() = preferences.edit().putBoolean(cmLangIsSelected,true).apply()

    fun isSelected(): Boolean = preferences.getBoolean(cmLangIsSelected,false)

    /*
    get string language
     */
    fun getLanguage(): String = preferences.getString(cmLangKey,"en")!!
}