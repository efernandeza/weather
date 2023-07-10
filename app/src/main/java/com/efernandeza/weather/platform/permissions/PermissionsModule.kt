package com.efernandeza.weather.platform.permissions

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PermissionsModule {
    @Singleton
    @Binds
    abstract fun bindPermissionsService(service: AndroidPermissionsService): PermissionsService
}
