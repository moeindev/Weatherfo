package ir.moeindeveloper.weatherfo.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ir.moeindeveloper.weatherfo.data.local.CityListProvider
import ir.moeindeveloper.weatherfo.data.model.Cities
import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import ir.moeindeveloper.weatherfo.data.preference.AppSettings
import ir.moeindeveloper.weatherfo.data.repository.WeatherRepository
import ir.moeindeveloper.weatherfo.util.network.Resource

class WeatherViewModel @ViewModelInject constructor(private val repository: WeatherRepository,
                                                    val settings: AppSettings,
                                                    private val cityProvider: CityListProvider): ViewModel() {



    val oneCall : LiveData<Resource<WeatherInfo>> = liveData {
        emit(Resource.loading(null))
        repository.oneCall(settings.getLat(),settings.getLon()).let { response ->
            if (response.isSuccessful){
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.errorBody().toString(),null))
            }
        }
    }


    val cities: LiveData<Cities> = liveData {
        emit(cityProvider.cities)
    }

}