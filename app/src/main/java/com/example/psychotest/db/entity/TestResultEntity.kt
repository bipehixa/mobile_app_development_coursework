package com.example.psychotest.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "results",
    foreignKeys = [ForeignKey(
        entity = TestEntity::class,
        parentColumns = ["id"],
        childColumns = ["testId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TestResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val testId: Long,
    val scoreStart: Int,
    val scoreEnd: Int,
    val description: String
)