package ir.moeindeveloper.weatherfo.data.repository

import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import ir.moeindeveloper.weatherfo.data.remote.WeatherApiHelper
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val helper: WeatherApiHelper) {

    suspend fun oneCall(latitude: Double?, longitude: Double?): Response<WeatherInfo> =
        helper.oneCall(latitude,longitude)

}