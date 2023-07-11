package com.efernandeza.weather.data

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.rxjava3.RxDataStore
import com.efernandeza.weather.data.forecast.WeatherForecast
import com.efernandeza.weather.data.geocode.GeocodeLocation
import com.efernandeza.weather.data.geocode.StateCountry
import com.efernandeza.weather.data.openweather.OpenWeatherService
import com.efernandeza.weather.platform.location.Location
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService,
    private val currentLocationDataStore: RxDataStore<Preferences>
) : WeatherRepository {
    private val PREF_KEY_LAT = doublePreferencesKey("latitude")
    private val PREF_KEY_LONG = doublePreferencesKey("longitude")

    override fun observeWeather(): Observable<WeatherForecast> {
        return currentLocationDataStore.data().map { prefs ->
            val lat = prefs[PREF_KEY_LAT]
            val long = prefs[PREF_KEY_LONG]
            if (lat != null && long != null) {
                Result.success(Location(latitude = lat, longitude = long))
            } else {
                // Given more time would do appropriate error handling.
                Result.failure(Exception("No coordinates to lookup."))
            }
        }.toObservable()
            .flatMap { result ->
                val location = result.getOrNull()
                return@flatMap if (result.isSuccess && location != null) {
                    openWeatherService.fetchWeather(
                        location.latitude.toString(),
                        location.longitude.toString()
                    ).map { weatherResponse ->
                        val weather = weatherResponse.weather.firstOrNull()
                        WeatherForecast(
                            id = weatherResponse.id,
                            name = weatherResponse.name,
                            dateTime = weatherResponse.dt,
                            currentTemp = weatherResponse.main.temp,
                            lowTemp = weatherResponse.main.temp_min,
                            highTemp = weatherResponse.main.temp_max,
                            iconUrl = "https://openweathermap.org/img/wn/" + weather?.icon + "@4x.png",
                            description = weather?.description
                        )
                    }
                } else {
                    // TODO : Use a relay to maintain subscription
                    Observable.empty()
                }
            }
    }

    override fun geocode(term: String): Observable<List<GeocodeLocation>> {
        return openWeatherService.geocode(term).map { geocodeItems ->
            geocodeItems.map { item ->
                val stateCountry = if (item.state != null &&
                    item.country != null
                ) {
                    StateCountry(state = item.state, country = item.country)
                } else {
                    null
                }

                GeocodeLocation(
                    name = item.name,
                    Location(latitude = item.lat, longitude = item.lon),
                    stateCountry = stateCountry
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setCurrentLocation(location: Location): Single<Preferences> {
        return currentLocationDataStore.updateDataAsync { prefs ->
            val mutablePrefs = prefs.toMutablePreferences()
            mutablePrefs[PREF_KEY_LAT] = location.latitude
            mutablePrefs[PREF_KEY_LONG] = location.longitude
            Single.just(mutablePrefs)
        }
    }
}
