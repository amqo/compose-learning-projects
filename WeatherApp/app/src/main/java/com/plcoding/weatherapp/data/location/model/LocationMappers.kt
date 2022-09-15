package com.plcoding.weatherapp.data.location.model

import android.location.Location
import com.plcoding.weatherapp.domain.location.model.LocationData

fun Location.toLocationData(): LocationData {
    return LocationData(
        latitude = latitude,
        longitude = longitude
    )
}