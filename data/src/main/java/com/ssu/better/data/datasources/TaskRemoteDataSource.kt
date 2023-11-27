package com.ssu.better.data.datasources

import com.ssu.better.data.services.TaskService
import com.ssu.better.entity.challenge.ChallengeRequest
import com.ssu.better.entity.task.TaskCreateRequest
import retrofit2.Retrofit

class TaskRemoteDataSource(
    retrofitPair: Pair<Retrofit, Retrofit>,
) {
    private val tokenTaskService = retrofitPair.second.create(TaskService::class.java)

    suspend fun getTask(id: Long) = tokenTaskService.getTask(taskId = id)

    suspend fun postCreateChallenge(taskId: Long, challengeRequest: ChallengeRequest) = tokenTaskService.postCreateTask(taskId, challengeRequest)

    suspend fun createTask(request: TaskCreateRequest) = tokenTaskService.createTask(request)
}
