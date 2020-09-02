package ir.moeindeveloper.weatherfo.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.moeindeveloper.weatherfo.data.Constants
import ir.moeindeveloper.weatherfo.data.remote.WeatherApiHelper
import ir.moeindeveloper.weatherfo.data.remote.WeatherApiImpl
import ir.moeindeveloper.weatherfo.data.remote.WeatherApiService
import ir.moeindeveloper.weatherfo.util.network.NetworkHelper
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper = NetworkHelper(context)


    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context, networkHelper: NetworkHelper): OkHttpClient {

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (networkHelper.isNetworkConnected())
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
            .build()
    }


    @Provides
    @Singleton
    fun provideIceAndFireRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PreferenceKey.prefs,Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherApiService::class.java)


    @Provides
    @Singleton
    fun provideApiHelper(apiImpl: WeatherApiImpl) : WeatherApiHelper = apiImpl
}