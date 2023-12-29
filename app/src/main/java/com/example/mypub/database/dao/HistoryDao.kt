package com.example.mypub.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.mypub.database.entities.History

@Dao
interface HistoryDao {
    @Insert
    fun insertHistory(history: History): Long
}