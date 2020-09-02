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
        val name = "Kermanshah"
        val lat: Double = 34.3277
        val lon: Double = 47.0778
    }

    const val DEFAULT_EXCLUDE = "minutely"
    const val DEFAULT_UNITS = "metric"
}