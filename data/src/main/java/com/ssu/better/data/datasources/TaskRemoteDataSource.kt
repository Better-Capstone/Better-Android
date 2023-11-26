package com.ssu.better.data.datasources

import com.ssu.better.data.services.TaskService
import retrofit2.Retrofit

class TaskRemoteDataSource(
    private val retrofitPair: Pair<Retrofit, Retrofit>,
    ) {
    private val tokenTaskService = retrofitPair.second.create(TaskService::class.java)

    suspend fun getTask(id: Long) = tokenTaskService.getTask(taskId = id)
}
