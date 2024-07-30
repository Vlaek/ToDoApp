package com.example.todo.network

data class Task (
    val id: Long,
    val title: String,
    val description: String,
    val completed: Boolean
)