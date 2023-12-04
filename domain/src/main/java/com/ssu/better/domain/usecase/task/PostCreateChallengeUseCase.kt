package com.ssu.better.domain.usecase.task

import com.ssu.better.domain.repository.TaskRepository
import com.ssu.better.entity.challenge.ChallengeRequest
import okhttp3.MultipartBody

class PostCreateChallengeUseCase constructor(
    private val taskRepository: TaskRepository,
) {
    suspend fun postCreateChallenge(taskId: Long, image: MultipartBody.Part, challengeRequest: ChallengeRequest) =
        taskRepository.postCreateChallenge(taskId, image, challengeRequest)
}
