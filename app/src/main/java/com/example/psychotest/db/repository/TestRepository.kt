package com.example.psychotest.db.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.psychotest.db.dao.TestDao
import com.example.psychotest.db.data.TestCard
import com.example.psychotest.db.entity.QuestionEntity
import com.example.psychotest.db.entity.TestEntity
import com.example.psychotest.db.entity.TestResultEntity
import com.example.psychotest.db.entity.TestSessionResultEntity
import javax.inject.Inject

class TestRepository @Inject constructor(private val testDao: TestDao) {

    // Возвращаем LiveData напрямую из DAO
    fun getAllTests(): LiveData<List<TestCard>> {
        return testDao.getAllTests().map { it.map { TestCard(it.id.toLong(), it.title) } }
    }

    fun getAllSessionResults(): LiveData<List<TestCard>> {
        return testDao.getAllTestSessions().map { it.map { TestCard(it.id.toLong(), it.testTitle, it.completionDate, it.userName, it.resultDescription) } }
    }

    suspend fun getQuestionsForTest(testId: Long): List<QuestionEntity> {
        return testDao.getQuestionsForTest(testId)
    }

    suspend fun insertTest(testCard: TestCard) {
        val testEntity = TestEntity(
            id = testCard.id.toInt(),
            title = testCard.title
        )
        testDao.insertTest(testEntity)
    }

    suspend fun insertQuestions(questions: List<QuestionEntity>) {
        testDao.insertQuestions(questions)
    }

    suspend fun insertResultSession(resultSessionResult: TestSessionResultEntity) {
        testDao.insertTestSessionResult(resultSessionResult)
    }

    suspend fun getResultsForTest(testId: Long): List<TestResultEntity> {
        return testDao.getResultsForTest(testId)
    }


}


