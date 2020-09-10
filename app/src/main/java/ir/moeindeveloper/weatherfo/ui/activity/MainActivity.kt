package ir.moeindeveloper.weatherfo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.moeindeveloper.weatherfo.R
import ir.moeindeveloper.weatherfo.data.model.City
import ir.moeindeveloper.weatherfo.data.model.Current
import ir.moeindeveloper.weatherfo.data.model.Daily
import ir.moeindeveloper.weatherfo.databinding.ActivityMainBinding
import ir.moeindeveloper.weatherfo.databinding.DialogSelectCityBinding
import ir.moeindeveloper.weatherfo.ui.adapter.CityAdapter
import ir.moeindeveloper.weatherfo.ui.adapter.DailyAdapter
import ir.moeindeveloper.weatherfo.ui.adapter.HourlyAdapter
import ir.moeindeveloper.weatherfo.util.date.DateEvents
import ir.moeindeveloper.weatherfo.util.date.getDateEvent
import ir.moeindeveloper.weatherfo.util.network.RequestStatus
import ir.moeindeveloper.weatherfo.util.ui.*
import ir.moeindeveloper.weatherfo.util.weather.getWeatherIcon
import ir.moeindeveloper.weatherfo.util.weather.toStringTemp
import ir.moeindeveloper.weatherfo.viewModel.WeatherViewModel
import kotlinx.android.synthetic.main.dialog_select_city.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.background = ContextCompat.getDrawable(this, getDateEvent().getGradientBackground())

    }

}