package com.efernandeza.weather.data.forecast

fun Double.toFahrenheit() = (1.8 * (this - 273) + 32).toInt()
