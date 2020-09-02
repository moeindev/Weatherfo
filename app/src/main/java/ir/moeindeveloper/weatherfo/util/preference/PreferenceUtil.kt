package ir.moeindeveloper.weatherfo.util.preference

import android.content.SharedPreferences

fun SharedPreferences.Editor.putDouble(key: String, double: Double){
    putLong(key,java.lang.Double.doubleToLongBits(double))
}

fun SharedPreferences.getDouble(key: String, default: Double): Double =
    java.lang.Double.longBitsToDouble(getLong(key,java.lang.Double.doubleToLongBits(default)))
