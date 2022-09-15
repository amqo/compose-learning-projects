package com.plcoding.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.weatherapp.domain.location.repository.LocationTracker
import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val weatherResource = weatherRepository.getWeatherInfo(
                    location.latitude,
                    location.longitude
                )
                state = when(weatherResource) {
                    is Resource.Success -> {
                        state.copy(
                            weatherInfo = weatherResource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = weatherResource.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location"
                )
            }
        }
    }
}