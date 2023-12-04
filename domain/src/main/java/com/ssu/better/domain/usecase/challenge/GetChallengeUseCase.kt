package com.ssu.better.domain.usecase.challenge

import com.ssu.better.domain.repository.ChallengeRepository

class GetChallengeUseCase(
    private val challengeRepository: ChallengeRepository,
) {
    suspend fun getChallenge(id: Long) = challengeRepository.getChallenge(id)
}
