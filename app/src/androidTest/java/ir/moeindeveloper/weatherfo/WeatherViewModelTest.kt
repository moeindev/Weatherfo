package ir.moeindeveloper.weatherfo

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import ir.moeindeveloper.weatherfo.data.local.CityListProvider
import ir.moeindeveloper.weatherfo.data.model.WeatherInfo
import ir.moeindeveloper.weatherfo.data.preference.AppSettings
import ir.moeindeveloper.weatherfo.data.repository.WeatherRepository
import ir.moeindeveloper.weatherfo.util.network.RequestStatus
import ir.moeindeveloper.weatherfo.util.network.Resource
import ir.moeindeveloper.weatherfo.viewModel.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.haroldadmin.cnradapter.NetworkResponse
import ir.moeindeveloper.weatherfo.util.MainCoroutineRule
import ir.moeindeveloper.weatherfo.util.getOrAwaitValue
import ir.moeindeveloper.weatherfo.util.observeForTesting
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @Rule
    @JvmField
    val instanceTaskExecutorRule  = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var weatherViewModel: WeatherViewModel


    //Initializing mocks:
    @RelaxedMockK
    lateinit var repository: WeatherRepository

    @RelaxedMockK
    lateinit var context: Context

    lateinit var sharedPreferences: SharedPreferences

    lateinit var appSettings: AppSettings

    @RelaxedMockK
    lateinit var cityListProvider: CityListProvider

    @Before
    fun before() {
        MockKAnnotations.init(this)

        sharedPreferences = context.getSharedPreferences(anyString(), anyInt())

        appSettings = AppSettings(context,sharedPreferences)

        weatherViewModel = WeatherViewModel(repository,appSettings,cityListProvider)

    }


    @ExperimentalCoroutinesApi
    @Test
    fun given_success_response_and_should_return_success_type() = runBlockingTest {

        //Given
        coEvery { repository.oneCall(anyDouble(), anyDouble()) } returns NetworkResponse.Success(fakeSuccessResponse(),code = 200)

        weatherViewModel.oneCall.observeForever{}

        //when
        weatherViewModel.loadData()

        assertThat(weatherViewModel.oneCall.value?.status).isEqualTo(RequestStatus.SUCCESS)

        return@runBlockingTest
    }

    @Test
    fun given_error_response_and_should_return_error_type() = runBlockingTest {

        //Given
        coEvery { repository.oneCall(anyDouble(), anyDouble()) } coAnswers { NetworkResponse.UnknownError(
            Throwable("Fake error")
        )}


        weatherViewModel.oneCall.observeForTesting {
        }

        //when
        weatherViewModel.loadData()


        //assertions
        assertThat(weatherViewModel.oneCall.getOrAwaitValue(10).status).isEqualTo(RequestStatus.ERROR)
        return@runBlockingTest
    }

    private fun fakeSuccessResponse(): WeatherInfo {
        val stringResponse = "{\"lat\":34.33,\"lon\":47.08,\"timezone\":\"Asia/Tehran\",\"timezone_offset\":16200,\"current\":{\"dt\":1600286535,\"sunrise\":1600310181,\"sunset\":1600354563,\"temp\":22,\"feels_like\":16.96,\"pressure\":1019,\"humidity\":13,\"dew_point\":-6.81,\"uvi\":9.31,\"clouds\":0,\"visibility\":10000,\"wind_speed\":3.1,\"wind_deg\":260,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}]},\"hourly\":[{\"dt\":1600286400,\"temp\":22,\"feels_like\":18.48,\"pressure\":1019,\"humidity\":13,\"dew_point\":-6.81,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.93,\"wind_deg\":357,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600290000,\"temp\":19.91,\"feels_like\":17,\"pressure\":1016,\"humidity\":21,\"dew_point\":-2.69,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.74,\"wind_deg\":60,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600293600,\"temp\":18.32,\"feels_like\":15.54,\"pressure\":1014,\"humidity\":26,\"dew_point\":-1.32,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.83,\"wind_deg\":91,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600297200,\"temp\":17.27,\"feels_like\":14.44,\"pressure\":1013,\"humidity\":28,\"dew_point\":-1.23,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.93,\"wind_deg\":74,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600300800,\"temp\":16.55,\"feels_like\":13.66,\"pressure\":1013,\"humidity\":31,\"dew_point\":-0.55,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.16,\"wind_deg\":71,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600304400,\"temp\":16.19,\"feels_like\":13.24,\"pressure\":1013,\"humidity\":31,\"dew_point\":-2.65,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.19,\"wind_deg\":76,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600308000,\"temp\":15.94,\"feels_like\":12.95,\"pressure\":1014,\"humidity\":32,\"dew_point\":-2.41,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.28,\"wind_deg\":79,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600311600,\"temp\":16.35,\"feels_like\":13.27,\"pressure\":1015,\"humidity\":30,\"dew_point\":-3.73,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.31,\"wind_deg\":82,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600315200,\"temp\":21.68,\"feels_like\":18.33,\"pressure\":1015,\"humidity\":22,\"dew_point\":-3.32,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.76,\"wind_deg\":91,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600318800,\"temp\":24.78,\"feels_like\":21.34,\"pressure\":1014,\"humidity\":18,\"dew_point\":-2.86,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.85,\"wind_deg\":95,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600322400,\"temp\":27.44,\"feels_like\":24.01,\"pressure\":1014,\"humidity\":15,\"dew_point\":-2.57,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.77,\"wind_deg\":105,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600326000,\"temp\":29.95,\"feels_like\":26.47,\"pressure\":1014,\"humidity\":13,\"dew_point\":-3.05,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.84,\"wind_deg\":122,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600329600,\"temp\":31.8,\"feels_like\":28.5,\"pressure\":1013,\"humidity\":12,\"dew_point\":-4.24,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.65,\"wind_deg\":150,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600333200,\"temp\":33,\"feels_like\":29.44,\"pressure\":1012,\"humidity\":10,\"dew_point\":-6.4,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.73,\"wind_deg\":208,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600336800,\"temp\":33.7,\"feels_like\":30.04,\"pressure\":1012,\"humidity\":10,\"dew_point\":-7.42,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.97,\"wind_deg\":230,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600340400,\"temp\":34.07,\"feels_like\":29.82,\"pressure\":1011,\"humidity\":9,\"dew_point\":-8.11,\"clouds\":0,\"visibility\":10000,\"wind_speed\":2.62,\"wind_deg\":241,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600344000,\"temp\":33.96,\"feels_like\":29.47,\"pressure\":1010,\"humidity\":9,\"dew_point\":-8.51,\"clouds\":0,\"visibility\":10000,\"wind_speed\":2.95,\"wind_deg\":242,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600347600,\"temp\":33.58,\"feels_like\":28.68,\"pressure\":1010,\"humidity\":9,\"dew_point\":-8.42,\"clouds\":0,\"visibility\":10000,\"wind_speed\":3.48,\"wind_deg\":237,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600351200,\"temp\":31.58,\"feels_like\":25.21,\"pressure\":1010,\"humidity\":11,\"dew_point\":-7.29,\"clouds\":0,\"visibility\":10000,\"wind_speed\":5.78,\"wind_deg\":263,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600354800,\"temp\":26.12,\"feels_like\":20.63,\"pressure\":1011,\"humidity\":15,\"dew_point\":-9.23,\"clouds\":0,\"visibility\":10000,\"wind_speed\":4.51,\"wind_deg\":275,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600358400,\"temp\":22.65,\"feels_like\":18.42,\"pressure\":1012,\"humidity\":17,\"dew_point\":-11.17,\"clouds\":0,\"visibility\":10000,\"wind_speed\":2.53,\"wind_deg\":288,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600362000,\"temp\":21.36,\"feels_like\":17.56,\"pressure\":1013,\"humidity\":18,\"dew_point\":-12.23,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.87,\"wind_deg\":300,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600365600,\"temp\":20.48,\"feels_like\":16.47,\"pressure\":1014,\"humidity\":19,\"dew_point\":-12.6,\"clouds\":0,\"visibility\":10000,\"wind_speed\":2.16,\"wind_deg\":303,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600369200,\"temp\":18.98,\"feels_like\":15.43,\"pressure\":1014,\"humidity\":21,\"dew_point\":-12.62,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.52,\"wind_deg\":295,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600372800,\"temp\":17.32,\"feels_like\":14.48,\"pressure\":1014,\"humidity\":23,\"dew_point\":-12.43,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.48,\"wind_deg\":280,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600376400,\"temp\":16.93,\"feels_like\":14,\"pressure\":1014,\"humidity\":24,\"dew_point\":-12.29,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.65,\"wind_deg\":66,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600380000,\"temp\":16.59,\"feels_like\":13.7,\"pressure\":1014,\"humidity\":26,\"dew_point\":-10.75,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.73,\"wind_deg\":83,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600383600,\"temp\":16.37,\"feels_like\":13.5,\"pressure\":1014,\"humidity\":27,\"dew_point\":-9.71,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.75,\"wind_deg\":94,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600387200,\"temp\":16.25,\"feels_like\":13.22,\"pressure\":1014,\"humidity\":27,\"dew_point\":-10.02,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.96,\"wind_deg\":77,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600390800,\"temp\":15.78,\"feels_like\":12.7,\"pressure\":1013,\"humidity\":27,\"dew_point\":-10.18,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.96,\"wind_deg\":77,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600394400,\"temp\":15.05,\"feels_like\":12.29,\"pressure\":1014,\"humidity\":30,\"dew_point\":-8.9,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.65,\"wind_deg\":66,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600398000,\"temp\":15.18,\"feels_like\":12.4,\"pressure\":1014,\"humidity\":29,\"dew_point\":-9.62,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.61,\"wind_deg\":42,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600401600,\"temp\":21.12,\"feels_like\":18.24,\"pressure\":1015,\"humidity\":20,\"dew_point\":-8.15,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.75,\"wind_deg\":55,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600405200,\"temp\":24.26,\"feels_like\":21.53,\"pressure\":1014,\"humidity\":17,\"dew_point\":-7.9,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.61,\"wind_deg\":60,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600408800,\"temp\":27.01,\"feels_like\":24.53,\"pressure\":1014,\"humidity\":14,\"dew_point\":-7.36,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.18,\"wind_deg\":322,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600412400,\"temp\":29.37,\"feels_like\":26.16,\"pressure\":1014,\"humidity\":12,\"dew_point\":-8.07,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.18,\"wind_deg\":278,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600416000,\"temp\":31.25,\"feels_like\":27.01,\"pressure\":1013,\"humidity\":11,\"dew_point\":-9.59,\"clouds\":0,\"visibility\":10000,\"wind_speed\":2.7,\"wind_deg\":278,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600419600,\"temp\":32.51,\"feels_like\":27.29,\"pressure\":1012,\"humidity\":9,\"dew_point\":-11.9,\"clouds\":0,\"visibility\":10000,\"wind_speed\":3.81,\"wind_deg\":282,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600423200,\"temp\":33.14,\"feels_like\":27.95,\"pressure\":1011,\"humidity\":9,\"dew_point\":-11.38,\"clouds\":0,\"visibility\":10000,\"wind_speed\":3.84,\"wind_deg\":299,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600426800,\"temp\":33.49,\"feels_like\":28.59,\"pressure\":1010,\"humidity\":9,\"dew_point\":-9.47,\"clouds\":0,\"visibility\":10000,\"wind_speed\":3.47,\"wind_deg\":308,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600430400,\"temp\":33.63,\"feels_like\":26.43,\"pressure\":1009,\"humidity\":10,\"dew_point\":-6.08,\"clouds\":0,\"visibility\":10000,\"wind_speed\":7.02,\"wind_deg\":302,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600434000,\"temp\":32.73,\"feels_like\":25.15,\"pressure\":1009,\"humidity\":11,\"dew_point\":-4.83,\"clouds\":0,\"visibility\":10000,\"wind_speed\":7.67,\"wind_deg\":301,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600437600,\"temp\":30.99,\"feels_like\":24.29,\"pressure\":1009,\"humidity\":12,\"dew_point\":-4.08,\"clouds\":0,\"visibility\":10000,\"wind_speed\":6.39,\"wind_deg\":299,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0},{\"dt\":1600441200,\"temp\":25.94,\"feels_like\":20.83,\"pressure\":1010,\"humidity\":17,\"dew_point\":-2.99,\"clouds\":0,\"visibility\":10000,\"wind_speed\":4.26,\"wind_deg\":289,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600444800,\"temp\":23.16,\"feels_like\":18.97,\"pressure\":1011,\"humidity\":20,\"dew_point\":-2.48,\"clouds\":0,\"visibility\":10000,\"wind_speed\":2.94,\"wind_deg\":287,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600448400,\"temp\":21.6,\"feels_like\":18.25,\"pressure\":1011,\"humidity\":22,\"dew_point\":-2.15,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.74,\"wind_deg\":300,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600452000,\"temp\":20.56,\"feels_like\":17.58,\"pressure\":1012,\"humidity\":24,\"dew_point\":-1.88,\"clouds\":0,\"visibility\":10000,\"wind_speed\":1.27,\"wind_deg\":312,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0},{\"dt\":1600455600,\"temp\":19.78,\"feels_like\":17.01,\"pressure\":1012,\"humidity\":25,\"dew_point\":-1.72,\"clouds\":0,\"visibility\":10000,\"wind_speed\":0.96,\"wind_deg\":307,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"pop\":0}],\"daily\":[{\"dt\":1600331400,\"sunrise\":1600310181,\"sunset\":1600354563,\"temp\":{\"day\":33,\"min\":16.71,\"max\":33.96,\"night\":20.48,\"eve\":26.12,\"morn\":16.71},\"feels_like\":{\"day\":29.44,\"night\":16.47,\"eve\":20.63,\"morn\":13.61},\"pressure\":1012,\"humidity\":10,\"dew_point\":-6.4,\"wind_speed\":1.73,\"wind_deg\":208,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":9.31},{\"dt\":1600417800,\"sunrise\":1600396623,\"sunset\":1600440877,\"temp\":{\"day\":32.51,\"min\":15.18,\"max\":33.63,\"night\":20.56,\"eve\":25.94,\"morn\":15.18},\"feels_like\":{\"day\":27.29,\"night\":17.58,\"eve\":20.83,\"morn\":12.4},\"pressure\":1012,\"humidity\":9,\"dew_point\":-11.9,\"wind_speed\":3.81,\"wind_deg\":282,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":8.89},{\"dt\":1600504200,\"sunrise\":1600483064,\"sunset\":1600527192,\"temp\":{\"day\":32.96,\"min\":16.63,\"max\":32.96,\"night\":18.55,\"eve\":24.68,\"morn\":16.63},\"feels_like\":{\"day\":24.18,\"night\":15.57,\"eve\":19.61,\"morn\":13.85},\"pressure\":1009,\"humidity\":11,\"dew_point\":-4.02,\"wind_speed\":9.42,\"wind_deg\":302,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":8.88},{\"dt\":1600590600,\"sunrise\":1600569506,\"sunset\":1600613506,\"temp\":{\"day\":30.8,\"min\":13.38,\"max\":32.33,\"night\":19.24,\"eve\":24.48,\"morn\":13.38},\"feels_like\":{\"day\":26.91,\"night\":15.1,\"eve\":19.19,\"morn\":11.3},\"pressure\":1009,\"humidity\":12,\"dew_point\":-4.4,\"wind_speed\":2.35,\"wind_deg\":303,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":8.77},{\"dt\":1600677000,\"sunrise\":1600655947,\"sunset\":1600699820,\"temp\":{\"day\":32.68,\"min\":14.46,\"max\":34.01,\"night\":20.65,\"eve\":24.46,\"morn\":14.46},\"feels_like\":{\"day\":27.92,\"night\":16.66,\"eve\":19.79,\"morn\":11.98},\"pressure\":1009,\"humidity\":10,\"dew_point\":-9.17,\"wind_speed\":3.4,\"wind_deg\":335,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":8.62},{\"dt\":1600763400,\"sunrise\":1600742389,\"sunset\":1600786134,\"temp\":{\"day\":32.71,\"min\":15.85,\"max\":33.42,\"night\":20.79,\"eve\":26.37,\"morn\":15.85},\"feels_like\":{\"day\":28.84,\"night\":17.6,\"eve\":21.18,\"morn\":12.8},\"pressure\":1009,\"humidity\":11,\"dew_point\":-3.41,\"wind_speed\":2.37,\"wind_deg\":283,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":8.54},{\"dt\":1600849800,\"sunrise\":1600828831,\"sunset\":1600872448,\"temp\":{\"day\":32.95,\"min\":17.01,\"max\":32.95,\"night\":18.36,\"eve\":24,\"morn\":17.24},\"feels_like\":{\"day\":23.61,\"night\":16.02,\"eve\":19.32,\"morn\":14.1},\"pressure\":1007,\"humidity\":11,\"dew_point\":-2.38,\"wind_speed\":10.22,\"wind_deg\":277,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":8.42},{\"dt\":1600936200,\"sunrise\":1600915273,\"sunset\":1600958763,\"temp\":{\"day\":27.68,\"min\":13.25,\"max\":29.71,\"night\":29.71,\"eve\":29.71,\"morn\":13.25},\"feels_like\":{\"day\":24.49,\"night\":27.52,\"eve\":27.52,\"morn\":10.79},\"pressure\":1010,\"humidity\":21,\"dew_point\":3.93,\"wind_speed\":2.5,\"wind_deg\":84,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":8.19}]}"

        return Gson().fromJson(stringResponse,WeatherInfo::class.java)
    }


}