package com.example.psychotest.db.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.psychotest.db.entity.QuestionEntity
import com.example.psychotest.db.entity.TestEntity
import com.example.psychotest.db.entity.TestResultEntity
import com.example.psychotest.db.entity.TestSessionResultEntity


@Dao
interface TestDao {

    @Query("SELECT * FROM tests")
    fun getAllTests(): LiveData<List<TestEntity>>

    @Query("SELECT * FROM questions WHERE testId = :testId")
    suspend fun getQuestionsForTest(testId: Long): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTest(test: TestEntity): Long // Используйте Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestResult(result: TestResultEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestSessionResult(result: TestSessionResultEntity)

    @Query("SELECT * FROM tests")
    fun getAllTestsSync(): List<TestEntity>

    @Query("SELECT * FROM results WHERE testId = :testId")
    suspend fun getResultsForTest(testId: Long): List<TestResultEntity>

    @Query("SELECT * FROM session_result")
    fun getAllTestSessions(): LiveData<List<TestSessionResultEntity>>
}


