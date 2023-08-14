package com.hw.notanothertodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hw.notanothertodo.objects.Prize
import com.hw.notanothertodo.objects.Task

//Prize table structure for room database
@Entity(tableName = "prizes")
data class Prize(
    @PrimaryKey(autoGenerate = true)
    val prizeID: Int = 0,
    var title: String,
    val prizeCostRange: String,
    var cost: Int = 0,
    var isStarred: Boolean = false,
    val userID: Int
)