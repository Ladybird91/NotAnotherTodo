package com.hw.notanothertodo.room

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getAllUsersStream(): Flow<List<User>>
    fun getUserStream(userID: Int): Flow<User?>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)

    fun getAllTasksStream(): Flow<List<Task>>
    fun getTaskStream(taskID: Int): Flow<Task?>
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)

    fun getAllPrizesStream(): Flow<List<Prize>>
    fun getPrizeStream(prizeID: Int): Flow<Prize?>
    suspend fun insertPrize(prize: Prize)
    suspend fun deletePrize(prize: Prize)
    suspend fun updatePrize(prize: Prize)

    fun getAllCategoriesStream(): Flow<List<Category>>
    fun getCategoryStream(categoryID: Int): Flow<Category?>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun updateCategory(category: Category)

}