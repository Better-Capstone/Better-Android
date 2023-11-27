package com.ssu.better.data.services

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeRequest
import com.ssu.better.entity.task.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskService {
    @GET("/task/{id}")
    suspend fun getTask(@Path("id") taskId: Long): Response<Task>

    @POST("/task/{id}/challenge/register")
    suspend fun postCreateTask(@Path("id") taskId: Long, @Body challengeRequest: ChallengeRequest): Response<Challenge>

    suspend fun createTask(
        @Body request: TaskCreateRequest,
    ): Response<Task>
}