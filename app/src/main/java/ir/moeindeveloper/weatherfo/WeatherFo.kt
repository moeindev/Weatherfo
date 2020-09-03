package ir.moeindeveloper.weatherfo

import android.app.Application
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherFo: Application() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this,"en")
    }
}