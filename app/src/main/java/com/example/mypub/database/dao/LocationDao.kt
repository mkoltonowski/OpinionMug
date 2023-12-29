package com.example.mypub.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.mypub.database.entities.Location

@Dao
interface LocationDao {
    @Insert
    fun insertLocation(location: Location) : Long
}