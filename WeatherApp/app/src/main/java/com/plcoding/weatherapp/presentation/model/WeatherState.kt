package com.plcoding.weatherapp.presentation.model

import com.plcoding.weatherapp.domain.weather.model.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val connectivityStatus: ConnectivityStatus = ConnectivityStatus.Unavailable
)
