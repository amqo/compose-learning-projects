package com.plcoding.weatherapp.domain.connectivity

import com.plcoding.weatherapp.domain.connectivity.model.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<ConnectivityStatus>
}