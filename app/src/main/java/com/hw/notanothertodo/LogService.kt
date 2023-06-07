package com.hw.notanothertodo

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}