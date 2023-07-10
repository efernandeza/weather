package com.efernandeza.weather.data.openweather.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodeResponseItem(
    val name: String,
    val country: String?,
    val lat: Double,
    val lon: Double,
    val state: String?
)
