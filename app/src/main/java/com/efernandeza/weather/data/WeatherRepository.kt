package com.efernandeza.weather.data

import androidx.datastore.preferences.core.Preferences
import com.efernandeza.weather.platform.location.Location
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun geocode(term: String): Observable<List<GeocodeLocation>>
    fun setCurrentLocation(location: Location): Single<Preferences>
}
