package com.ssu.better.domain.usecase.task

import com.ssu.better.domain.repository.TaskRepository
import com.ssu.better.entity.challenge.ChallengeRequest

class PostCreateChallengeUseCase constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun postCreateChallenge(taskId: Long, challengeRequest: ChallengeRequest) = taskRepository.postCreateTask(taskId, challengeRequest)
}
