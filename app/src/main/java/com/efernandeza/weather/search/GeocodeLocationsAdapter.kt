package com.efernandeza.weather.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efernandeza.weather.databinding.ItemGeocodeLocationBinding
import com.efernandeza.weather.platform.location.Location

class GeocodeLocationsAdapter(private val viewModel: SearchViewModel) :
    ListAdapter<GeocodeLocationItem, GeocodeLocationViewHolder>(GeocodeLocationDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeocodeLocationViewHolder {
        val binding = ItemGeocodeLocationBinding.inflate(LayoutInflater.from(parent.context))
        return GeocodeLocationViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: GeocodeLocationViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }
}

data class GeocodeLocationItem(val name: String, val stateCountry: String?, val location: Location)

class GeocodeLocationViewHolder(
    private val binding: ItemGeocodeLocationBinding,
    private val viewModel: SearchViewModel
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: GeocodeLocationItem) {
        binding.geocodeLocationName.text = item.name
        binding.geocodeLocationStateCountry.text = item.stateCountry
        binding.root.setOnClickListener { viewModel.updateCurrentLocation(item.location) }
    }
}

private class GeocodeLocationDiffCallback : DiffUtil.ItemCallback<GeocodeLocationItem>() {
    override fun areItemsTheSame(
        oldItem: GeocodeLocationItem,
        newItem: GeocodeLocationItem
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: GeocodeLocationItem,
        newItem: GeocodeLocationItem
    ): Boolean = oldItem == newItem
}
