package com.hw.notanothertodo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDao {

    // user specific
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
    @Update
    suspend fun update(user: User)
    @Delete
    suspend fun delete(user: User)
    @Query("SELECT * from users WHERE userID = :id")
    fun getUser(id: Int): Flow<User>
    @Query("SELECT * from users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>



    //Task Specific
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)
    @Update
    suspend fun update(task: Task)
    @Delete
    suspend fun delete(task: Task)
    @Query("SELECT * from tasks WHERE taskID = :id")
    fun getTask(id: Int): Flow<Task>
    @Query("SELECT * from tasks ORDER BY title ASC")
    fun getAllTasks(): Flow<List<Task>>

    //prize specific
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(prize: Prize)
    @Update
    suspend fun update(prize: Prize)
    @Delete
    suspend fun delete(prize: Prize)
    @Query("SELECT * from prizes WHERE prizeID = :id")
    fun getPrize(id: Int): Flow<Prize>
    @Query("SELECT * from prizes ORDER BY title ASC")
    fun getAllPrizes(): Flow<List<Prize>>


    //category specific
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)
    @Update
    suspend fun update(category: Category)
    @Delete
    suspend fun delete(category: Category)
    @Query("SELECT * from categories WHERE categoryID = :id")
    fun getCategory(id: Int): Flow<Category>
    @Query("SELECT * from categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<Category>>

    //db functions
    @Transaction
    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun getUserTasks(name: String): List<UserWithTasks>

    @Transaction
    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun getUserPrizes(name: String): List<UserWithPrizes>

    @Transaction
    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun getUserCategories(name: String): List<UserWithCategories>

    @Transaction
    @Query("SELECT * FROM categories WHERE name = :name")
    suspend fun getCategoryTasks(name: String): List<CategoryWithTasks>
}