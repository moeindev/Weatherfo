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
class MainActivity : AppCompatActivity(), OnDailyForecastListener,CitySelectListener {

    private val vm by viewModels<WeatherViewModel>()

    private lateinit var binding: ActivityMainBinding

    private val hourlyAdapter =  HourlyAdapter()

    private val dailyAdapter = DailyAdapter(this)

    private lateinit var dialogBinding: DialogSelectCityBinding

    private val cityAdapter = CityAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.background = ContextCompat.getDrawable(this, getDateEvent().getGradientBackground())
        binding.mainLayout.changeLocation.text = vm.settings.getName()
        binding.mainLayout.hourlyList.adapter = hourlyAdapter
        binding.mainLayout.dailyList.adapter = dailyAdapter
        //binding.mainLayout.dailyList.setHasFixedSize(true)
        binding.mainLayout.changeLocation.setOnClickListener {
            initCityDialog()
        }

        binding.noConnectionLayout.tryAgainButton.setOnClickListener {
            vm.loadData()
        }
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
        vm.cities.observe(this, Observer {
            cityAdapter.updateData(it.cities)
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


    private lateinit var dialog: MaterialDialog
    private fun initCityDialog(){
        dialogBinding = DialogSelectCityBinding.inflate(layoutInflater,null,false)
        dialogBinding.dialogCityList.adapter = cityAdapter
        dialog = MaterialDialog(this,BottomSheet()).show {
            customView(view = dialogBinding.root)
        }
    }

    override fun onCitySelected(city: City) {
        dialog.dismiss()
        vm.settings.saveCity(city.name,city.coord.lat,city.coord.lon)
        vm.loadData()
        binding.mainLayout.changeLocation.text = city.name
    }

    override fun onResume() {
        super.onResume()
        vm.loadData()
    }
}