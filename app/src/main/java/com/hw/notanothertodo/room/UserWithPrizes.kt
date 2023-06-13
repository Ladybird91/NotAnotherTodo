package com.hw.notanothertodo.room

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPrizes(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userID",
        entityColumn =  "userID"
    )
    val prizes: List<Prize>
)