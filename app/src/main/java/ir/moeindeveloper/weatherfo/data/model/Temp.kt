package ir.moeindeveloper.weatherfo.data.model


import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("morn")
    val morn: Double,
    @SerializedName("day")
    val day: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("max")
    val max: Double,
    @SerializedName("min")
    val min: Double,
)