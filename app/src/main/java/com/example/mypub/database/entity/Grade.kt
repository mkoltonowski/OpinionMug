package com.example.mypub.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "grade",
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("locationId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Grade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val locationId: Int,
    val value: Int
)