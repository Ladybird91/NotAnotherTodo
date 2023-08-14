package com.hw.notanothertodo.room

import kotlinx.coroutines.flow.Flow


class OfflineTodoRepository(private val toDao: ToDao): TodoRepository {

    // user specific functions
    override fun getAllUsersStream(): Flow<List<User>> = toDao.getAllUsers()
    override fun getUserStream(userID: Int): Flow<User?> = toDao.getUser(userID)
    override suspend fun insertUser(user: User) = toDao.insert(user)
    override suspend fun deleteUser(user: User) = toDao.delete(user)
    override suspend fun updateUser(user: User) = toDao.update(user)


    // task specific functions
    override fun getAllTasksStream(): Flow<List<Task>> = toDao.getAllTasks()
    override fun getTaskStream(taskID: Int): Flow<Task?> = toDao.getTask(taskID)
    override suspend fun insertTask(task: Task) = toDao.insert(task)
    override suspend fun deleteTask(task: Task) = toDao.delete(task)
    override suspend fun updateTask(task: Task) = toDao.update(task)


    // prize specific functions
    override fun getAllPrizesStream(): Flow<List<Prize>> = toDao.getAllPrizes()
    override fun getPrizeStream(prizeID: Int): Flow<Prize?> = toDao.getPrize(prizeID)
    override suspend fun insertPrize(prize: Prize) = toDao.insert(prize)
    override suspend fun deletePrize(prize: Prize) = toDao.delete(prize)
    override suspend fun updatePrize(prize: Prize) = toDao.update(prize)



    // category specific functions
    override fun getAllCategoriesStream(): Flow<List<Category>> = toDao.getAllCategories()
    override fun getCategoryStream(categoryID: Int): Flow<Category?> = toDao.getCategory(categoryID)
    override suspend fun insertCategory(category: Category) = toDao.insert(category)
    override suspend fun deleteCategory(category: Category) = toDao.delete(category)
    override suspend fun updateCategory(category: Category) = toDao.update(category)
}