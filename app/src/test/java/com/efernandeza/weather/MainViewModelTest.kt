package com.efernandeza.weather

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.efernandeza.weather.platform.permissions.PermissionsService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class MainViewModelTest {
    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var androidSchedulerRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var testObserver: Observer<MainViewModel.ViewState>

    private val permissionsService: PermissionsService = mock()

    private lateinit var viewModel: MainViewModel

    @Test
    fun `Given main view model init When coarse location is not permitted Then request permission state`() {
        whenever(permissionsService.hasPermission(ACCESS_COARSE_LOCATION))
            .thenReturn(false)

        viewModel = MainViewModel(permissionsService)
        viewModel.viewState
            .observeForever(testObserver)

        verify(testObserver).onChanged(MainViewModel.ViewState.RequestLocationPermission)
    }

    @Test
    fun `Given main view model init When coarse location is already permitted Then nav to search state`() {
        whenever(permissionsService.hasPermission(any()))
            .thenReturn(true)

        viewModel = MainViewModel(permissionsService)
        viewModel.viewState
            .observeForever(testObserver)

        verify(testObserver).onChanged(MainViewModel.ViewState.NavToSearch)
    }
}
