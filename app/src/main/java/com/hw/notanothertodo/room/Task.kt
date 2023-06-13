package com.hw.notanothertodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskID: Int = 0,
    val title: String,
    val priority: String,
    val difficulty: String,
    val points: Int = 0,
    val completed: Boolean,
    val userID: Int,
    val categoryID: Int,
    )


