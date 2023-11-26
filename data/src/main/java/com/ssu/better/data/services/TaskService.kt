package com.ssu.better.data.services

import com.ssu.better.entity.task.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskService {
    @GET("/task/{id}")
    suspend fun getTask(@Path("id") taskId: Long): Response<Task>
}
