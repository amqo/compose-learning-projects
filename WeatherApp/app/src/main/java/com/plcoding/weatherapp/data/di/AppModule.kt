package com.plcoding.weatherapp.data.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.plcoding.weatherapp.data.weather.repository.remote.WeatherApi
import com.plcoding.weatherapp.domain.connectivity.ConnectivityObserver
import com.plcoding.weatherapp.data.connectivity.NetworkConnectivityObserver
import com.plcoding.weatherapp.data.sensors.HumiditySensor
import com.plcoding.weatherapp.data.sensors.LightSensor
import com.plcoding.weatherapp.data.sensors.TemperatureSensor
import com.plcoding.weatherapp.domain.sensors.MeasurableSensor
import com.plcoding.weatherapp.domain.sensors.MeasurableSensorList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        app: Application
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        app: Application
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(app)
    }

    @Provides
    @Singleton
    fun provideSensors(
        app: Application
    ): MeasurableSensorList {
        return MeasurableSensorList(
            sensors = listOf(
                TemperatureSensor(app),
                HumiditySensor(app),
                LightSensor(app)
            )
        )
    }
}