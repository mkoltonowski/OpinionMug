package com.example.mypub.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypub.database.entities.Location
import com.example.mypub.repository.LocationRepository
import kotlinx.coroutines.launch

class LocationViewModel(private val repository: LocationRepository) : ViewModel()  {
    val allLocations: LiveData<List<Location>> = repository.getAllLocations()

    fun insert(location: Location) = viewModelScope.launch {
        repository.insert(location)
    }
}