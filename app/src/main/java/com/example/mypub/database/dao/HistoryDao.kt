package com.example.mypub.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mypub.database.entities.History

@Dao
interface HistoryDao {
    @Insert
    fun insertHistory(history: History): Long

    @Query("SELECT * FROM history")
    fun getAllHistories(): LiveData<List<History>>
}