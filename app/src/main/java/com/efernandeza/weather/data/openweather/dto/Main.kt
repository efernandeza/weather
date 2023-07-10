package com.efernandeza.weather.data.openweather.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)
