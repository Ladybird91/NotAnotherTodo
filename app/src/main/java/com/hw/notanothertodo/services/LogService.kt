package com.hw.notanothertodo.services

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}