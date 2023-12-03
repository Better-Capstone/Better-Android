package com.ssu.better.domain.repository

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeRequest
import com.ssu.better.entity.task.Task
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface TaskRepository {
    suspend fun getTask(id: Long): Flow<Task>

    suspend fun postCreateChallenge(id: Long, image: MultipartBody.Part, challengeRequest: ChallengeRequest): Flow<Challenge>
}
