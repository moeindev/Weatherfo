package ir.moeindeveloper.weatherfo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.moeindeveloper.weatherfo.R
import ir.moeindeveloper.weatherfo.data.model.Current
import ir.moeindeveloper.weatherfo.data.model.Daily
import ir.moeindeveloper.weatherfo.databinding.ActivityMainBinding
import ir.moeindeveloper.weatherfo.ui.adapter.DailyAdapter
import ir.moeindeveloper.weatherfo.ui.adapter.HourlyAdapter
import ir.moeindeveloper.weatherfo.util.date.DateEvents
import ir.moeindeveloper.weatherfo.util.date.getDateEvent
import ir.moeindeveloper.weatherfo.util.network.RequestStatus
import ir.moeindeveloper.weatherfo.util.ui.*
import ir.moeindeveloper.weatherfo.util.weather.getWeatherIcon
import ir.moeindeveloper.weatherfo.util.weather.toStringTemp
import ir.moeindeveloper.weatherfo.viewModel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnDailyForecastListener {

    private val vm by viewModels<WeatherViewModel>()

    private lateinit var binding: ActivityMainBinding

    private val hourlyAdapter =  HourlyAdapter()

    private val dailyAdapter = DailyAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.background = ContextCompat.getDrawable(this, getDateEvent().getGradientBackground())
        binding.mainLayout.changeLocation.text = vm.settings.getName()
        binding.mainLayout.hourlyList.adapter = hourlyAdapter
        binding.mainLayout.dailyList.adapter = dailyAdapter
        //binding.mainLayout.dailyList.setHasFixedSize(true)
        observeVM()
    }



    private fun observeVM(){
        vm.oneCall.observe(this, Observer { info->
            when(info.status){
                RequestStatus.LOADING -> {
                    binding.enterLoadingState()
                }

                RequestStatus.ERROR -> {
                    binding.enterErrorState()
                }


                RequestStatus.SUCCESS -> {
                    binding.enterSuccessState()
                    info.data?.let { data ->
                        updateCurrentUi(data.current)
                        hourlyAdapter.updateData(data.hourly)
                        dailyAdapter.updateData(data.daily)
                    }
                }
            }
        })
    }


    override fun onSelected(daily: Daily) {
        binding.mainLayout.mainTemp.text = when(getDateEvent()) {
            DateEvents.DAWN -> daily.temp.morn.toStringTemp()
            DateEvents.MORNING -> daily.temp.morn.toStringTemp()
            DateEvents.NOON -> daily.temp.eve.toStringTemp()
            DateEvents.EVENING -> daily.temp.eve.toStringTemp()
            DateEvents.NIGHT -> daily.temp.night.toStringTemp()
        }
        binding.mainLayout.feelsTemp.text = when(getDateEvent()){
            DateEvents.DAWN -> daily.feelsLike.morn.toStringTemp()
            DateEvents.MORNING -> daily.feelsLike.morn.toStringTemp()
            DateEvents.NOON -> daily.feelsLike.eve.toStringTemp()
            DateEvents.EVENING -> daily.feelsLike.eve.toStringTemp()
            DateEvents.NIGHT -> daily.feelsLike.night.toStringTemp()
        }

        if (daily.weather.isNotEmpty()) {
            //get image
            Glide.with(this).load(daily.weather[0].icon.getWeatherIcon()).into(binding.mainLayout.mainTempIcon)
        }

        //show snackBar:
        val snack = Snackbar.make(binding.root, R.string.snack_other_days,Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.show_today){
                vm.oneCall.value?.let {
                    it.data?.let {
                        updateCurrentUi(it.current)
                    }
                }
            }

        snack.show()
    }


    private fun updateCurrentUi(current: Current){
        binding.mainLayout.mainTemp.text = current.temp.toStringTemp()
        binding.mainLayout.feelsTemp.text = "${current.feelsLike.toStringTemp()}/${current.dewPoint.toStringTemp()}"
        if (current.weather.isNotEmpty()) {
            //get image
            Glide.with(this).load(current.weather[0].icon.getWeatherIcon()).into(binding.mainLayout.mainTempIcon)
        }
    }

}