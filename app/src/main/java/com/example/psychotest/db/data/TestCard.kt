package com.example.psychotest.db.data

data class TestCard(
    val id: Long,
    val title: String,
    val completionDate: Long = 0,
    val name: String? = null,
    val description: String? = null
)