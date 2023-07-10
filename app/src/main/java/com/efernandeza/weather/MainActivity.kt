package com.efernandeza.weather

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.efernandeza.weather.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
            navigateToSearch()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.viewState.observe(this, ::handleViewState)
    }

    private fun handleViewState(viewState: MainViewModel.ViewState) {
        when (viewState) {
            MainViewModel.ViewState.NavToSearch -> navigateToSearch()
            MainViewModel.ViewState.RequestLocationPermission -> requestLocationPermission()
        }
    }

    private fun navigateToSearch() {
        findNavController(R.id.nav_host_fragment_content_main)
            .navigate(R.id.action_LoadingFragment_to_SearchFragment)
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)) {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.rationale_title_coarse_location))
                .setMessage(resources.getString(R.string.rationale_msg_coarse_location))
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                    requestPermission.launch(ACCESS_COARSE_LOCATION)
                }
                .show()
        } else {
            requestPermission.launch(ACCESS_COARSE_LOCATION)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}
