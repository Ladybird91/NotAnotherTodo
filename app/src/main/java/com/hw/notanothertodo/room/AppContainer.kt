package com.hw.notanothertodo.room


import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val todoRepository: TodoRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineTodoRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [TodoRepository]
     */
    override val todoRepository: TodoRepository by lazy {
        OfflineTodoRepository(ToDatabase.getDatabase(context).toDao())
    }
}