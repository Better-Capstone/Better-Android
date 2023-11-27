package com.ssu.better.domain.repository

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeRequest
import com.ssu.better.entity.task.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTask(id: Long): Flow<Task>

    suspend fun postCreateTask(id: Long, challengeRequest: ChallengeRequest): Flow<Challenge>

    suspend fun createTask(request: TaskCreateRequest): Flow<Task>
}