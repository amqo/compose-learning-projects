package com.plcoding.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.weatherapp.domain.connectivity.ConnectivityObserver
import com.plcoding.weatherapp.domain.connectivity.model.ConnectivityStatus
import com.plcoding.weatherapp.domain.location.repository.LocationTracker
import com.plcoding.weatherapp.domain.sensors.MeasurableSensorList
import com.plcoding.weatherapp.domain.sensors.SensorType
import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.repository.WeatherRepository
import com.plcoding.weatherapp.presentation.model.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository,
    private val connectivityObserver: ConnectivityObserver,
    sensorList: MeasurableSensorList
): ViewModel() {

    var weatherState by mutableStateOf(WeatherState())
        private set

    init {
        sensorList.sensors.forEach { sensor ->
            Log.d("AMQO", "Start listening sensor: " + sensor.sensorType)
            sensor.startListening()
            sensor.setOnSensorValuesChangedListener { values ->
                when(sensor.sensorType) {
                    SensorType.Humidity -> onHumiditySensorValues(values)
                    SensorType.Temperature -> onTemperatureSensorValues(values)
                    SensorType.Light -> onLightSensorValues(values)
                    SensorType.Unknown -> Unit
                }
            }
        }
    }

    private fun onTemperatureSensorValues(values: List<Float>) {
        val temperature = values[0]
        Log.d(TAG, "onTemperatureSensorValues $temperature")
    }

    private fun onHumiditySensorValues(values: List<Float>) {
        val humidity = values[0]
        Log.d(TAG, "onHumiditySensorValues $humidity")
    }

    private fun onLightSensorValues(values: List<Float>) {
        val light = values[0]
        Log.d(TAG, "onLightSensorValues $light")
        weatherState = weatherState.copy(
            lightValue = light.roundToInt()
        )
    }

    fun loadInfo() {
        connectivityObserver.observe().onEach { status ->
            if (status == ConnectivityStatus.Available) {
                weatherState = weatherState.copy(
                    connectivityStatus = status
                )
                loadWeatherInfo()
            } else {
                weatherState = weatherState.copy(
                    weatherInfo = null,
                    isLoading = false,
                    connectivityStatus = status
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            weatherState = weatherState.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val weatherResource = weatherRepository.getWeatherInfo(
                    location.latitude,
                    location.longitude
                )
                weatherState = when(weatherResource) {
                    is Resource.Success -> {
                        weatherState.copy(
                            weatherInfo = weatherResource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        weatherState.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = weatherResource.message
                        )
                    }
                }
            } ?: kotlin.run {
                weatherState = weatherState.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location"
                )
            }
        }
    }

    companion object {
        private const val TAG = "WeatherViewModel"
    }
}