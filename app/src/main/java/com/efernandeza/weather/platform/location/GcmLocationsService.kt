package com.efernandeza.weather.platform.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import com.efernandeza.weather.platform.permissions.PermissionsService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GcmLocationsService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val permissionsService: PermissionsService

) : LocationsService {

    private val fuzedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(locationListener: (Location) -> Unit) {
        if (permissionsService.hasPermission(ACCESS_COARSE_LOCATION)) {
            val task = fuzedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_LOW_POWER,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token
                    override fun isCancellationRequested(): Boolean = false
                }
            )
            task.addOnSuccessListener { platformLocation ->
                locationListener.invoke(
                    Location(
                        latitude = platformLocation.latitude,
                        longitude = platformLocation.longitude
                    )
                )
            }
        }
    }
}
