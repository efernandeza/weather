package com.efernandeza.weather.data.geocode

import com.efernandeza.weather.platform.location.Location

data class GeocodeLocation(
    val name: String,
    val location: Location,
    val stateCountry: StateCountry? = null
)

data class StateCountry(val state: String, val country: String)
