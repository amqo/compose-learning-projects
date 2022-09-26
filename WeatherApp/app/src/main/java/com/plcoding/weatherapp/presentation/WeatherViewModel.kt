package com.plcoding.weatherapp.presentation

import android.util.Log
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
import kotlinx.coroutines.flow.*
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

    private val _weatherStateFlow = MutableStateFlow(WeatherState())
    val weatherStateFlow = _weatherStateFlow.asStateFlow()

    init {
        sensorList.sensors.forEach { sensor ->
            Log.d(TAG, "Start listening sensor: " + sensor.sensorType)
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
        _weatherStateFlow.update {
            _weatherStateFlow.value.copy(
                lightValue = light.roundToInt()
            )
        }
    }

    fun loadInfo() {
        connectivityObserver.observe().onEach { status ->
            if (status == ConnectivityStatus.Available) {
                _weatherStateFlow.update {
                    _weatherStateFlow.value.copy(
                        connectivityStatus = status
                    )
                }
                loadWeatherInfo()
            } else {
                _weatherStateFlow.update {
                    _weatherStateFlow.value.copy(
                        weatherInfo = null,
                        isLoading = false,
                        connectivityStatus = status
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            _weatherStateFlow.update {
                weatherStateFlow.value.copy(
                    isLoading = true,
                    error = null
                )
            }
            locationTracker.getCurrentLocation()?.let { location ->
                val weatherResource = weatherRepository.getWeatherInfo(
                    location.latitude,
                    location.longitude
                )
                _weatherStateFlow.update {
                    when (weatherResource) {
                        is Resource.Success -> {
                            _weatherStateFlow.value.copy(
                                weatherInfo = weatherResource.data,
                                isLoading = false,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            _weatherStateFlow.value.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = weatherResource.message
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                _weatherStateFlow.update {
                    weatherStateFlow.value.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location"
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "WeatherViewModel"
    }
}