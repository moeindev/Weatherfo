package ir.moeindeveloper.weatherfo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ir.moeindeveloper.weatherfo.R
import ir.moeindeveloper.weatherfo.viewModel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}