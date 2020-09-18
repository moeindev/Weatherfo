package ir.moeindeveloper.weatherfo.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import ir.moeindeveloper.weatherfo.data.local.CityListProvider
import ir.moeindeveloper.weatherfo.data.model.Cities
import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import ir.moeindeveloper.weatherfo.data.preference.AppSettings
import ir.moeindeveloper.weatherfo.data.repository.WeatherRepository
import ir.moeindeveloper.weatherfo.util.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(private val repository: WeatherRepository,
                                                    val settings: AppSettings,
                                                    private val cityProvider: CityListProvider): ViewModel() {

    @ExperimentalCoroutinesApi
    val oneCall : LiveData<Resource<WeatherInfo>> = liveData {
        commandsChannel.consumeEach {
            emit(Resource.loading(null))
            when(val response = repository.oneCall(settings.getLat(),settings.getLon())) {

                is NetworkResponse.Success -> {
                    emit(Resource.success(response.body))
                }

                is NetworkResponse.NetworkError -> {
                    emit(Resource.error("Network Error!",null))
                }

                is NetworkResponse.ServerError -> {
                    emit(Resource.error("server Error",null))
                }

                is NetworkResponse.UnknownError -> {
                    emit(Resource.error("unknown error",null))
                }
            }
        }
    }


    val cities: LiveData<Cities> = liveData {
        emit(cityProvider.cities)
    }

    private val commandsChannel = Channel<String>()

    fun loadData(){
        viewModelScope.launch {
            commandsChannel.send("reload")
        }
    }
}