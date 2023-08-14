package com.hw.notanothertodo.room

data class UserUiState(

    val userDetails: UserDetails = UserDetails(),
    val isEntryValid: Boolean = false,
)

data class UserDetails(
    val userID: Int = 0,
    val name: String = "",
    val email: String = "",
    val currentPoints: Int = 0,
    val lifetimePoints: Int = 0,
)

fun UserDetails.toUser(): User = User(
        userID = userID,
        name = name,
        email = email,
        currentPoints = currentPoints,
        lifetimePoints = lifetimePoints,
)

fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
        userDetails = this.toUserDetails(),
        isEntryValid = isEntryValid,
)
fun User.toUserDetails(): UserDetails = UserDetails(
        userID = userID,
        name = name,
        email = email,
        currentPoints = currentPoints,
        lifetimePoints = lifetimePoints,
)