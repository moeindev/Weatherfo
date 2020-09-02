package ir.moeindeveloper.weatherfo.data.remote

import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import retrofit2.Response
import javax.inject.Inject

class WeatherApiImpl @Inject constructor(private val service: WeatherApiService): WeatherApiHelper {


    override suspend fun oneCall(latitude: Double?, longitude: Double?): Response<WeatherInfo> =
        service.oneCall(latitude,longitude)


}