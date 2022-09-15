package com.plcoding.weatherapp.data.di

import com.plcoding.weatherapp.data.weather.repository.WeatherRepositoryImpl
import com.plcoding.weatherapp.domain.weather.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {

    @Binds
    @Singleton
    abstract fun provideWeatherRepository(
        weatherRepository: WeatherRepositoryImpl
    ): WeatherRepository
}