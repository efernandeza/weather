package com.efernandeza.weather.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efernandeza.weather.data.GeocodeLocation
import com.efernandeza.weather.data.WeatherRepository
import com.efernandeza.weather.platform.location.Location
import com.efernandeza.weather.platform.location.LocationsService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val locationsService: LocationsService,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<SearchViewModel.ViewState>()

    private val compositeDisposable = CompositeDisposable()

    val viewState: LiveData<SearchViewModel.ViewState>
        get() = _viewState

    fun search(term: String) {
        val disposable = weatherRepository.geocode(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturnItem(emptyList()) // Given more time would add observability
            .subscribe(::onGeocodeResult)

        compositeDisposable.add(disposable)
    }

    private fun onGeocodeResult(geocodeLocations: List<GeocodeLocation>) {
        _viewState.value = ViewState.SearchResult(geocodeLocations = geocodeLocations)
    }

    fun updateCurrentLocation(location: Location) {
        val disposable = weatherRepository.setCurrentLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorComplete() // Given more time would add observability
            .subscribe()

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    sealed class ViewState {
        data class SearchResult(val geocodeLocations: List<GeocodeLocation>) : ViewState()
    }
}
