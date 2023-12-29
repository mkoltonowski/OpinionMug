package com.example.mypub.repository

import androidx.lifecycle.LiveData
import com.example.mypub.database.dao.GradeDao
import com.example.mypub.database.entities.Grade

class GradeRepository (private val gradeDao: GradeDao) {
    suspend fun insert(grade: Grade) {
        gradeDao.insertGrade(grade)
    }

    fun getAllGrades(): LiveData<List<Grade>> {
        return gradeDao.getAllGrades()
    }
}