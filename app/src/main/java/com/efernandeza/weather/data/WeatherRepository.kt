package com.efernandeza.weather.data

import androidx.datastore.preferences.core.Preferences
import com.efernandeza.weather.data.forecast.WeatherForecast
import com.efernandeza.weather.data.geocode.GeocodeLocation
import com.efernandeza.weather.platform.location.Location
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun geocode(term: String): Observable<List<GeocodeLocation>>
    fun setCurrentLocation(location: Location): Single<Preferences>
    fun observeWeather(): Observable<WeatherForecast>
}
