package com.example.psychotest.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = TestEntity::class,
        parentColumns = ["id"],
        childColumns = ["testId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val testId: Long,
    val text: String
)