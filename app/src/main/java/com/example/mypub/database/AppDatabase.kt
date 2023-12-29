package com.example.mypub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mypub.database.dao.GradeDao
import com.example.mypub.database.dao.HistoryDao
import com.example.mypub.database.dao.LocationDao
import com.example.mypub.database.entities.Grade
import com.example.mypub.database.entities.History
import com.example.mypub.database.entities.Location


@Database(entities = [Location::class, Grade::class, History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun gradeDao(): GradeDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // migration strategy - optional
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}