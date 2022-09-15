package com.plcoding.weatherapp.data.weather.repository

import com.plcoding.weatherapp.data.weather.model.toWeatherInfo
import com.plcoding.weatherapp.data.weather.repository.remote.WeatherApi
import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.model.WeatherInfo
import com.plcoding.weatherapp.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherInfo(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat, long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Error getting weather Info", null)
        }
    }
}