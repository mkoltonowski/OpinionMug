package com.example.mypub.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mypub.database.entities.Grade

@Dao
interface GradeDao {
    @Insert
    fun insertGrade(grade: Grade): Long

    @Query("SELECT * FROM grades")
    fun getAllGrades(): LiveData<List<Grade>>
}