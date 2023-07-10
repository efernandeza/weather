package com.efernandeza.weather.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideWeatherRepository(repository: OpenWeatherRepository): WeatherRepository {
        return repository
    }

    @Singleton
    @Provides
    fun provideCurrentLocationDataStore(@ApplicationContext context: Context): RxDataStore<Preferences> {
        return RxPreferenceDataStoreBuilder(context, name = "current_location").build()
    }
}
