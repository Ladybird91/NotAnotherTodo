package com.hw.notanothertodo.room

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithTasks(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryID",
        entityColumn = "categoryID"
    )
    val tasks: List<Task>
)