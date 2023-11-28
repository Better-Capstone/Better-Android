package com.ssu.better.domain.usecase.challenge

import com.ssu.better.domain.repository.ChallengeRepository
import com.ssu.better.entity.challenge.ChallengeVerifyRequest

class PostChallengeCommentUseCase(
    private val challengeRepository: ChallengeRepository,
) {
    suspend fun postChallengeComment(id: Long, challengeVerifyRequest: ChallengeVerifyRequest) =
        challengeRepository.postChallengeComment(id, challengeVerifyRequest)
}
