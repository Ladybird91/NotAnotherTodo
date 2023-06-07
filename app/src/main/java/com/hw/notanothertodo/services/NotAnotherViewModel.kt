package com.hw.notanothertodo.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hw.notanothertodo.services.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class NotAnotherViewModel (private val logService: LogService) : ViewModel() {
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->

                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}

