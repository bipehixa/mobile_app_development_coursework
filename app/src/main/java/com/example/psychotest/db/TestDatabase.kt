package com.example.psychotest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.psychotest.db.dao.TestDao
import com.example.psychotest.db.data.TestCard
import com.example.psychotest.db.entity.QuestionEntity
import com.example.psychotest.db.entity.TestEntity
import com.example.psychotest.db.entity.TestResultEntity
import com.example.psychotest.db.entity.TestSessionResultEntity

@Database(entities = [TestEntity::class, QuestionEntity::class, TestSessionResultEntity::class, TestResultEntity::class], version = 1)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao

    companion object {
        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getDatabase(context: Context): TestDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java,
                    "test_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
