package com.efernandeza.weather.platform.location

interface LocationsService {
    fun getCurrentLocation(locationListener: (Location) -> Unit)
}
