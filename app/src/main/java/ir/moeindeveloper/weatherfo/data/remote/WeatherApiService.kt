package ir.moeindeveloper.weatherfo.data.remote

import ir.moeindeveloper.weatherfo.data.Constants
import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("onecall")
    suspend fun oneCall(@Query(Constants.QueryParams.lat) lat: Double? = Constants.DefaultLocation.lat,
                        @Query(Constants.QueryParams.lon) lon: Double? = Constants.DefaultLocation.lon,
                        @Query(Constants.QueryParams.exclude) exclude: String? = Constants.DEFAULT_EXCLUDE,
                        @Query(Constants.QueryParams.units) units: String? = Constants.DEFAULT_UNITS,
                        @Query(Constants.QueryParams.appID) appID: String? = Constants.APP_ID): Response<WeatherInfo>

}