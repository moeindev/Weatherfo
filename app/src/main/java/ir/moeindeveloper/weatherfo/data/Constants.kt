package ir.moeindeveloper.weatherfo.data

object Constants {

    object QueryParams {
        const val lat: String = "lat"
        const val lon: String = "lon"
        const val units: String = "units"
        const val exclude: String = "exclude"
        const val appID: String = "appid"
    }

    const val APP_ID: String = "df7a59e82ea51b578410407643c32e4d"

    const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"

    object DefaultLocation {
        const val name = "Kermanshah"
        const val lat: Double = 34.3277
        const val lon: Double = 47.0778
    }

    const val DEFAULT_EXCLUDE = "minutely"
    const val DEFAULT_UNITS = "metric"

    object PreferenceKey {
        const val prefs: String = "settings_pref"
        const val isSaved: String = "prefs_saved"
        const val name: String = "prefs_name"
        const val lat: String = "prefs_lat"
        const val lon: String = "prefs_lon"
    }

}