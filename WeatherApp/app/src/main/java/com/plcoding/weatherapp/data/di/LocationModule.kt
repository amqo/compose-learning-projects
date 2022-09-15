package com.plcoding.weatherapp.data.di

import com.plcoding.weatherapp.data.location.repository.LocationTrackerImpl
import com.plcoding.weatherapp.domain.location.repository.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        locationTracker: LocationTrackerImpl
    ): LocationTracker
}