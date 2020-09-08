package ir.moeindeveloper.weatherfo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.widget.textChangeEvents
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.moeindeveloper.weatherfo.R
import ir.moeindeveloper.weatherfo.data.model.City
import ir.moeindeveloper.weatherfo.databinding.FragmentCityBinding
import ir.moeindeveloper.weatherfo.ui.adapter.CityAdapter
import ir.moeindeveloper.weatherfo.util.ui.CitySelectListener
import ir.moeindeveloper.weatherfo.util.ui.focusOnSearchBox
import ir.moeindeveloper.weatherfo.util.ui.hideKeyboard
import ir.moeindeveloper.weatherfo.viewModel.WeatherViewModel
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CityFragment : Fragment(), CitySelectListener {

    private lateinit var binding: FragmentCityBinding


    private val vm by viewModels<WeatherViewModel>()

    private val cityAdapter = CityAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCityBinding.inflate(inflater,container,false)
        binding.mainLayout.dialogCityList.adapter = cityAdapter

        observeVM()
        initFiltering()
        return binding.root
    }


    private fun initFiltering() {
        binding.mainLayout.searchTEdit.textChangeEvents()
            .skipInitialValue()
            .debounce(300,TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                cityAdapter.filter.filter(it.text)
            }
    }


    override fun onResume() {
        super.onResume()
        binding.focusOnSearchBox(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().hideKeyboard()
    }

    private fun observeVM(){
        vm.cities.observe(viewLifecycleOwner, Observer {
            cityAdapter.updateData(it.cities)
        })
    }


    override fun onCitySelected(city: City) {
        vm.settings.saveCity(city.name,city.coord.lat,city.coord.lon)
        findNavController().popBackStack()
    }


}