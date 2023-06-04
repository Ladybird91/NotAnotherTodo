package com.hw.notanothertodo.objects

data class Prize (
    var title: String,
    val prizeCostRange: String,
    var cost: Int = 0,
    var isStarred: Boolean = false,
)

fun Prize.calculateCost(): Int {
    val prizeCost = when (prizeCostRange) {
        "Cheap" -> (30..50).random()
        "Affordable" -> (55..75).random()
        "Mid-range" -> (80..100).random()
        "Premium" -> (105..125).random()
        else -> 0
    }

    return prizeCost
}
