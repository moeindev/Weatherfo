package ir.moeindeveloper.weatherfo.data.model

import com.google.gson.annotations.SerializedName

data class WeatherInfoError(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: String
)