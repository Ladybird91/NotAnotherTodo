package com.hw.notanothertodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hw.notanothertodo.objects.Prize
import com.hw.notanothertodo.objects.Task


@Entity(tableName = "prizes")
data class Prize(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    val prizeCostRange: String,
    var cost: Int = 0,
    var isStarred: Boolean = false
)