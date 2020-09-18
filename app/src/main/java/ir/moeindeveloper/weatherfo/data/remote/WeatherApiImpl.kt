package ir.moeindeveloper.weatherfo.data.remote

import com.haroldadmin.cnradapter.NetworkResponse
import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import ir.moeindeveloper.weatherfo.data.model.WeatherInfoError
import retrofit2.Response
import javax.inject.Inject

class WeatherApiImpl @Inject constructor(private val service: WeatherApiService): WeatherApiHelper {


    override suspend fun oneCall(latitude: Double?, longitude: Double?): NetworkResponse<WeatherInfo,WeatherInfoError> =
        service.oneCall(latitude,longitude)

}