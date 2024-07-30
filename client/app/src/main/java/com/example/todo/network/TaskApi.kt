package com.example.todo.network

import retrofit2.Response
import retrofit2.http.*

interface TaskApi {
    @GET("/tasks")
        suspend fun getTasks(): Response<List<Task>>

    @POST("/tasks")
        suspend fun createTask(@Body task: Task): Response<Task>

    @PUT("/tasks/{id}")
        suspend fun updateTask(@Path("id") id: Long, @Body task: Task): Response<Task>

    @DELETE("/tasks/{id}")
        suspend fun deleteTask(@Path("id") id: Long): Response<Void>
}