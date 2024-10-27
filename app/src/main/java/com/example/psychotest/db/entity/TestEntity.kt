package com.example.psychotest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String
)