package com.efernandeza.weather.platform.permissions

interface PermissionsService {
    fun hasPermission(permission: String): Boolean
}
