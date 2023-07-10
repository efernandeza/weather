package com.efernandeza.weather.data.openweather.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    val id: Int,
    val icon: String,
    val description: String,
    val main: String
)
