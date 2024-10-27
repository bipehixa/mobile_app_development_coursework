package com.example.psychotest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychotest.db.data.TestCard
import com.example.psychotest.db.entity.QuestionEntity
import com.example.psychotest.db.entity.TestResultEntity
import com.example.psychotest.db.entity.TestSessionResultEntity
import com.example.psychotest.db.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val repository: TestRepository) : ViewModel() {

    //HelloFragment
    private var userName: String = "Аноним"

    //MenuFragment
    var allTests: MutableLiveData<List<TestCard>> = repository.getAllTests() as MutableLiveData<List<TestCard>>
    var allSessionResults: MutableLiveData<List<TestCard>> = repository.getAllSessionResults() as MutableLiveData<List<TestCard>>
    val mode: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentTest: MutableLiveData<TestCard> = MutableLiveData()

    //ResultFragment
    var score: Int = 0


    fun getQuestionsForTest(): LiveData<List<String>> {
        val questionsLiveData = MutableLiveData<List<String>>()
        viewModelScope.launch {
            val questions = repository.getQuestionsForTest(currentTest.value!!.id).map { it.text }
            questionsLiveData.postValue(questions)
        }
        return questionsLiveData
    }

    private suspend fun getResultForTest(): List<TestResultEntity> {
        return repository.getResultsForTest(currentTest.value!!.id)
    }

    fun updateRes() {
        allSessionResults.value = allSessionResults.value
    }

    fun updateTest() {
        allTests.value = allTests.value
    }

    fun setResult() {
        viewModelScope.launch {
            val results = getResultForTest()
            Log.d("result", "$results")
            for (result in results) {
                if (score in result.scoreStart..result.scoreEnd) {
                    Log.d("FIND", "$result")
                    currentTest.value = currentTest.value?.copy(description = (currentTest.value!!.name + result.description))
                }
            }

            val currentTimeMillis = System.currentTimeMillis()
            currentTest.value = currentTest.value?.copy(completionDate = currentTimeMillis)

            val cur = currentTest.value!!
            Log.d("CUR", "$cur")
            val newTestSessionResultEntity = TestSessionResultEntity(
                id = 0,
                testTitle = cur.title,
                userName = cur.name!!,
                completionDate = currentTimeMillis,
                resultDescription = cur.description!!
            )

            Log.d("newRes", newTestSessionResultEntity.toString())
            repository.insertResultSession(newTestSessionResultEntity)
        }
    }

    fun changeName(newName: String) {
        if (newName == "")
            userName = "Аноним"
        else
            userName = newName
        Log.d("NEW_NAME", userName)
    }

    fun getUserName(): String {
        return userName
    }

    fun setTest(testCard: TestCard) {
        currentTest.value = testCard.copy(name = userName)
    }


    fun testMode(){
        viewModelScope.launch {
            if (mode.value == true) mode.value = false

        }
        updateTest()
    }

    fun resultMode() {
        viewModelScope.launch {
            if (mode.value == false) mode.value = true

        }
        updateRes()
    }
}
