package ir.moeindeveloper.weatherfo.data.model

import com.google.gson.annotations.SerializedName

data class Cities(
    @SerializedName("cities") val cities: List<City>
)