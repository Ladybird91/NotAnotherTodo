package com.hw.notanothertodo.room

import androidx.room.Embedded
import androidx.room.Relation

//Table for linking multiple categories to each user
data class UserWithCategories(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userID",
        entityColumn = "userID"
    )
    val categories: List<Category>
)