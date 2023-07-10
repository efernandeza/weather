package com.efernandeza.weather.data.forecast

// Took shortcuts here with nullable types, some fields should not ever be null
data class WeatherForecast(
    val id: Int? = null,
    val name: String? = null,
    val dateTime: Long? = null,
    val currentTemp: Double? = null,
    val lowTemp: Double? = null,
    val highTemp: Double? = null,
    val iconUrl: String? = null
)
