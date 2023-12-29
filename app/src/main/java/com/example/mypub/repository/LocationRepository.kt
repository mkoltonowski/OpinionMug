package com.example.mypub.repository

import androidx.lifecycle.LiveData
import com.example.mypub.database.dao.LocationDao
import com.example.mypub.database.entities.Location

class LocationRepository(private val locationDao: LocationDao) {
    suspend fun insert(location: Location) {
        locationDao.insertLocation(location)
    }

    fun getAllLocations(): LiveData<List<Location>> {
        return locationDao.getAllLocations()
    }
}