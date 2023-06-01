package com.hw.notanothertodo.objects

data class Task(
    val name: String,
    val category: Category,
    val priority: String,
    val difficulty: String,
    var points: Int = 0,
    var checked: Boolean = false
)


fun Task.calculatePoints(): Int {
    val difficultyPoints = when (difficulty) {
        "Easy" -> (10..25).random()
        "Moderate" -> (30..45).random()
        "Hard" -> (50..65).random()
        else -> 0
    }

    val priorityPoints = when (priority) {
        "Medium" -> 10
        "High" -> 20
        else -> 0
    }

    return difficultyPoints + priorityPoints
}
