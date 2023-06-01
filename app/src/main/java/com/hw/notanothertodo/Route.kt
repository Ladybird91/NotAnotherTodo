package com.hw.notanothertodo

sealed class Routes(val route: String) {
    object Login : Routes("LoginView")
    object Signup : Routes("UserSignup")
    object Reset : Routes("PasswordReset")
}
