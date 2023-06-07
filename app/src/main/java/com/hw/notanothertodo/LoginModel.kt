package com.hw.notanothertodo

import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

class LoginModel @Inject constructor(
    logService: LogService
) : NotAnotherViewModel(logService) {

}