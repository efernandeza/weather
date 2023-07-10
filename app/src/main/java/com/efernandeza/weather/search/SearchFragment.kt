package com.efernandeza.weather.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.efernandeza.weather.R
import com.efernandeza.weather.databinding.FragmentSearchBinding
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var geocodeLocationsAdapter: GeocodeLocationsAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupSearchView(binding.searchView, binding.searchResultsList)
        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)

        return binding.root
    }

    private fun setupSearchView(searchView: SearchView, searchResultsList: RecyclerView) {
        searchView.editText.doAfterTextChanged { editable ->
            editable?.trim()?.let { text ->
                if (text.length >= 3) {
                    viewModel.search(term = text.toString())
                }
            }
        }
        geocodeLocationsAdapter = GeocodeLocationsAdapter(viewModel)
        binding.searchResultsList.apply {
            layoutManager = LinearLayoutManager(this@SearchFragment.context)
            adapter = geocodeLocationsAdapter
        }
    }

    private fun handleViewState(viewState: SearchViewModel.ViewState) {
        when (viewState) {
            is SearchViewModel.ViewState.SearchResult -> updateSearchResults(viewState)
            is SearchViewModel.ViewState.WeatherUpdate -> updateWeatherRendering(viewState)
        }
    }

    private fun updateSearchResults(searchResult: SearchViewModel.ViewState.SearchResult) {
        val locations = searchResult.geocodeLocations.map {
            val stateCountry = (it.stateCountry?.state ?: "") + " " + (it.stateCountry?.country ?: "")
            GeocodeLocationItem(
                name = it.name,
                stateCountry = stateCountry.trim().ifEmpty { null },
                location = it.location
            )
        }
        geocodeLocationsAdapter.submitList(locations)
    }

    private fun updateWeatherRendering(viewState: SearchViewModel.ViewState.WeatherUpdate) {
        if (binding.searchBar.isExpanding) {
            binding.searchBar.collapse(binding.searchView)
        }
        val forecast = viewState.weatherForecast
        binding.locationName.text = forecast.name
        // TODO : Locale specific temperature rendering for degrees F or C
        binding.locationCurrentTemp.text = resources.getString(R.string.degrees, forecast.currentTemp)
        binding.locationCurrentWeatherImage.load(forecast.iconUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
