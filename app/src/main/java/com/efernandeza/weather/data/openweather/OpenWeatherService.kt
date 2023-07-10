package com.efernandeza.weather.data.openweather

import com.efernandeza.weather.data.openweather.dto.GeocodeResponseItem
import com.efernandeza.weather.data.openweather.dto.WeatherForecastResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("geo/1.0/direct?limit=10")
    fun geocode(@Query("q") term: String): Observable<List<GeocodeResponseItem>>

    @GET("data/2.5/weather")
    fun fetchWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Observable<WeatherForecastResponse>
}
