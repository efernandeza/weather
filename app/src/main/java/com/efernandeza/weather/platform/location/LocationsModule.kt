package com.efernandeza.weather.platform.location

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationsModule {
    @Singleton
    @Binds
    abstract fun bindLocationsService(service: GcmLocationsService): LocationsService
}
