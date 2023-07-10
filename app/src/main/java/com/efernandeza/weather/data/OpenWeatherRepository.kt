package com.efernandeza.weather.data

import com.efernandeza.weather.data.openweather.OpenWeatherService
import com.efernandeza.weather.platform.location.Location
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherRepository @Inject constructor(
    private val openWeatherService: OpenWeatherService
) : WeatherRepository {
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

    override fun setCurrentLocation(location: Location) {
        TODO("Not yet implemented")
    }
}
