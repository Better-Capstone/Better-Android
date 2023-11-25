package com.ssu.better.data.services

import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskCreateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskService {
    @POST("/task/register")
    suspend fun createTask(
        @Body request: TaskCreateRequest,
    ): Response<Task>
}
