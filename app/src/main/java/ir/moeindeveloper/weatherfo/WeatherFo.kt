package ir.moeindeveloper.weatherfo

import android.app.Application
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import ir.moeindeveloper.weatherfo.data.preference.AppSettings
import javax.inject.Inject

@HiltAndroidApp
class WeatherFo: Application() {
    @Inject
    lateinit var appSettings: AppSettings

    override fun onCreate() {
        super.onCreate()
        appSettings.initLanguage(this)
    }
}