package com.plcoding.weatherapp.data.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.plcoding.weatherapp.domain.sensors.MeasurableSensor

abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String,
    private val androidSensorType: Int
): MeasurableSensor(
    sensorType = androidSensorType.toSensorType()
), SensorEventListener {

    override val doesSensorExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun startListening() {
        if (!doesSensorExist) {
            Log.d(TAG, "Sensor not available: $sensorType")
            return
        }
        if (!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(SensorManager::class.java) as SensorManager
            sensor = sensorManager.getDefaultSensor(androidSensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun stopListening() {
        if (!doesSensorExist || !::sensorManager.isInitialized) return
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if (!doesSensorExist) return
        if (sensorEvent?.sensor?.type == androidSensorType) {
            onSensorValuesChanged?.invoke(sensorEvent.values.toList())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit

    companion object {
        private const val TAG = "AndroidSensor"
    }
}