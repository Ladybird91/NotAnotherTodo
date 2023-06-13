package com.hw.notanothertodo.room

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTasks(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userID",
        entityColumn =  "userID")
    val tasks: List<Task>
    )
