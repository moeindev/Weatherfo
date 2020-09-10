package ir.moeindeveloper.weatherfo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
import ir.moeindeveloper.weatherfo.databinding.DialogSelectCityBinding
import ir.moeindeveloper.weatherfo.databinding.FragmentHomeBinding
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
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class HomeFragment : Fragment(), OnDailyForecastListener {

    private lateinit var binding: FragmentHomeBinding

    private val vm by viewModels<WeatherViewModel>()

    private val hourlyAdapter =  HourlyAdapter()

    private val dailyAdapter = DailyAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.mainLayout.hourlyList.adapter = hourlyAdapter
        binding.mainLayout.dailyList.adapter = dailyAdapter
        //binding.mainLayout.dailyList.setHasFixedSize(true)
        binding.mainLayout.changeLocation.setOnClickListener {
            val navigateToCity = HomeFragmentDirections.actionHomeFragmentToCityFragment()
            findNavController().navigate(navigateToCity)
        }

        binding.noConnectionLayout.tryAgainButton.setOnClickListener {
            vm.loadData()
        }

        /*
        Hot fix on translation not working in rtl position:
         */
        val dimen = resources.getDimension(R.dimen.mainTempIconTranslationX)
        if (vm.settings.getLanguage() == "fa") binding.mainLayout.mainTempIcon.translationX = -dimen
        //end of hot fix

        observeVM()

        binding.mainLayout.mainChangeLanguage.setOnClickListener {
            binding.showChangeLanguageDialog(vm.settings.getLanguage()){language ->
                vm.settings.saveLanguage(language)
                requireActivity().recreate()
            }
        }

        return binding.root
    }

    @ExperimentalCoroutinesApi
    private fun observeVM(){
        vm.oneCall.observe(viewLifecycleOwner, Observer { info->
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
        val snack = Snackbar.make(binding.root, R.string.snack_other_days, Snackbar.LENGTH_INDEFINITE)
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
        binding.mainLayout.feelsTemp.text = "${current.feelsLike.toStringTemp()} / ${current.dewPoint.toStringTemp()}"
        if (current.weather.isNotEmpty()) {
            //get image
            Glide.with(this).load(current.weather[0].icon.getWeatherIcon()).into(binding.mainLayout.mainTempIcon)
        }
    }

    override fun onResume() {
        super.onResume()
        vm.loadData()
        binding.mainLayout.changeLocation.text = vm.settings.getLocaleName()
    }

}