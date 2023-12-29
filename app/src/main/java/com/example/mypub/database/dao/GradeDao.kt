package com.example.mypub.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.mypub.database.entities.Grade

@Dao
interface GradeDao {
    @Insert
    fun insertGrade(grade: Grade): Long
}