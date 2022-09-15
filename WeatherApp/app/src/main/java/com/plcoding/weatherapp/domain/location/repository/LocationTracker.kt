package com.plcoding.weatherapp.domain.location.repository

import com.plcoding.weatherapp.domain.location.model.LocationData

interface LocationTracker {

    suspend fun getCurrentLocation(): LocationData?
}