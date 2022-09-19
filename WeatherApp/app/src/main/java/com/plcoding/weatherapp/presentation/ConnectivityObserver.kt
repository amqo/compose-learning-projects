package com.plcoding.weatherapp.presentation

import com.plcoding.weatherapp.presentation.model.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<ConnectivityStatus>
}