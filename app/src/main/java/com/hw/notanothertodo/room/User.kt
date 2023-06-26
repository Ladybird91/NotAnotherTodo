package com.hw.notanothertodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID: Int = 0,
    val name: String,
    val email: String,
    val currentPoints: Int = 0,
    val lifetimePoints: Int = 0,
)