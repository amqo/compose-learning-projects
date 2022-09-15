package com.plcoding.weatherapp.domain.weather.repository

import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo>
}