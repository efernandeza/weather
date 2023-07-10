package com.efernandeza.weather.data.openweather.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastResponse(
    val id: Int,
    val dt: Long,
    val main: Main,
    val name: String,
    val timezone: Int,
    val weather: List<Weather>
)
