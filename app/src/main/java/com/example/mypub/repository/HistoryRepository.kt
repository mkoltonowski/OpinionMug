package com.example.mypub.repository

import androidx.lifecycle.LiveData
import com.example.mypub.database.dao.HistoryDao
import com.example.mypub.database.entities.History

class HistoryRepository(private val historyDao: HistoryDao) {
    suspend fun insert(history: History) {
        historyDao.insertHistory(history)
    }

    fun getAllHistories(): LiveData<List<History>> {
        return historyDao.getAllHistories()
    }
}