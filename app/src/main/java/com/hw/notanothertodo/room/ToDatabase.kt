package com.hw.notanothertodo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [User::class, Task::class, Prize::class, Category::class],
    version = 1,
    exportSchema = false)
abstract class ToDatabase : RoomDatabase() {
    abstract fun toDao(): ToDao

    companion object {
        @Volatile
        private var Instance: ToDatabase? = null

        fun getDatabase(context: Context): ToDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ToDatabase::class.java, "to_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}