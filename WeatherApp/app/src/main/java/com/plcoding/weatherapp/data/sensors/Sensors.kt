package com.plcoding.weatherapp.data.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

data class TemperatureSensor(
    private val context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE,
    androidSensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
)

data class HumiditySensor(
    private val context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY,
    androidSensorType = Sensor.TYPE_RELATIVE_HUMIDITY
)

data class LightSensor(
    private val context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    androidSensorType = Sensor.TYPE_LIGHT
)