package com.hw.notanothertodo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface PrizeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(prize: Prize)

    @Update
    suspend fun update(prize: Prize)

    @Delete
    suspend fun delete(prize: Prize)

    @Query("SELECT * from prizes WHERE id = :id")
    fun getPrize(id: Int): Flow<Prize>

    @Query("SELECT * from prizes ORDER BY title ASC")
    fun getAllPrizes(): Flow<List<Prize>>
}