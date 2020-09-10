package ir.moeindeveloper.weatherfo.data.model


import com.google.gson.annotations.SerializedName
import ir.moeindeveloper.weatherfo.util.ui.adapter.AdapterObject

data class City(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    override val dt: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("faName")
    val faName: String,
    @SerializedName("state")
    val state: String
): AdapterObject<Int>