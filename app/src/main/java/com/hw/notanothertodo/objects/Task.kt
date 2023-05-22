package com.hw.notanothertodo.objects

data class Task(
    val name: String,
    val category: Category,
    val priority: String,
    val difficulty: String,
    var checked: Boolean = false
)
