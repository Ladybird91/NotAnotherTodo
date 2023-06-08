package com.hw.notanothertodo.login

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import com.hw.notanothertodo.RESET
import com.hw.notanothertodo.services.AccountService
import com.hw.notanothertodo.services.LogService
import com.hw.notanothertodo.services.NotAnotherViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : NotAnotherViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun onLoginEnter(){
        launchCatching {
            accountService.authenticate(email, password)
        }
    }

    fun String.isValidEmail(): Boolean {
        return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(RESET)

}