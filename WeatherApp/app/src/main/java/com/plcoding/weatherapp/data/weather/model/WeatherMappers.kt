package com.plcoding.weatherapp.data.weather.model

import com.plcoding.weatherapp.domain.weather.model.WeatherData
import com.plcoding.weatherapp.domain.weather.model.WeatherInfo
import com.plcoding.weatherapp.domain.weather.model.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val weatherData: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val weatherData = WeatherData(
            time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
            temperatureCelsius = temperatures[index],
            pressure = pressures[index],
            windSpeed = windSpeeds[index],
            humidity = humidities[index],
            weatherType = WeatherType.fromWeatherCode(weatherCodes[index])
        )
        IndexedWeatherData(
            index = index,
            weatherData = weatherData
        )
    }.groupBy {
        it.index / 24
    }.mapValues { mapValue ->
        mapValue.value.map {
            it.weatherData
        }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}