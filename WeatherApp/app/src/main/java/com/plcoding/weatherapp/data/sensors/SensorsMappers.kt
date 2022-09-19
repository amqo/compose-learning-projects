package com.plcoding.weatherapp.data.sensors

import android.hardware.Sensor
import com.plcoding.weatherapp.domain.sensors.SensorType

fun Int.toSensorType(): SensorType {
    return when(this) {
        Sensor.TYPE_AMBIENT_TEMPERATURE -> SensorType.Temperature
        Sensor.TYPE_RELATIVE_HUMIDITY -> SensorType.Humidity
        Sensor.TYPE_LIGHT -> SensorType.Light
        else -> SensorType.Unknown
    }
}