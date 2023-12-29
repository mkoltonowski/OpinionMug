package com.example.mypub.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mypub.database.entities.Location

@Dao
interface LocationDao {
    @Insert
    fun insertLocation(location: Location) : Long

    @Query("SELECT * FROM locations")
    fun getAllLocations(): LiveData<List<Location>>
}