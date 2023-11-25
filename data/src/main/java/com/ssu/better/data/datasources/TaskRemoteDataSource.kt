package com.ssu.better.data.datasources

import com.ssu.better.data.services.TaskService
import com.ssu.better.entity.task.TaskCreateRequest
import retrofit2.Retrofit
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    private val retrofitPair: Pair<Retrofit, Retrofit>,
) {
    private val tokenTaskService = retrofitPair.second.create(TaskService::class.java)

    suspend fun createTask(request: TaskCreateRequest) = tokenTaskService.createTask(request)
}
