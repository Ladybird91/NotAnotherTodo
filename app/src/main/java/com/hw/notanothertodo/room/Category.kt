package com.hw.notanothertodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hw.notanothertodo.objects.Task


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryID: Int = 0,
    val name: String,
    val userID: Int
)