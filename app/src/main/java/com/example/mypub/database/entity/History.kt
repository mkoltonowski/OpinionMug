package com.example.mypub.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    tableName = "history",
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("locationId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val locationId: Int,
    val date: String,
    val details: String
)