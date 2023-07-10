package com.efernandeza.weather

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efernandeza.weather.platform.permissions.PermissionsService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    permissionsService: PermissionsService
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()

    val viewState: LiveData<ViewState>
        get() = _viewState

    init {
        if (permissionsService.hasPermission(ACCESS_COARSE_LOCATION)) {
            _viewState.value = ViewState.NavToSearch
        } else {
            _viewState.value = ViewState.RequestLocationPermission
        }
    }

    // Since this view model is currently only side effects, modeling them as ViewStates
    // Otherwise, we could emit stable states with side effect properties.
    sealed class ViewState {
        data object RequestLocationPermission : ViewState()
        data object NavToSearch : ViewState()
    }
}
