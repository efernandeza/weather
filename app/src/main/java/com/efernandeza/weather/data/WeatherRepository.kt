package com.efernandeza.weather.data

import com.efernandeza.weather.platform.location.Location
import io.reactivex.rxjava3.core.Observable

interface WeatherRepository {
    fun geocode(term: String): Observable<List<GeocodeLocation>>
    fun setCurrentLocation(location: Location)
}
