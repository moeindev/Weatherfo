package ir.moeindeveloper.weatherfo.data.remote

import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import retrofit2.Response

interface WeatherApiHelper {

    suspend fun oneCall(latitude: Double?, Longitude: Double?): Response<WeatherInfo>

}