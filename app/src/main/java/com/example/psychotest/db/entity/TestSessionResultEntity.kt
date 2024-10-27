package com.example.psychotest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_result")
data class TestSessionResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val testTitle: String,
    val userName: String,
    val completionDate: Long,
    val resultDescription: String
)