package com.efernandeza.weather.platform.permissions

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidPermissionsService @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionsService {
    override fun hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED
}
