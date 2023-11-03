package com.ssu.better.domain.usecase

import com.ssu.better.domain.repository.UserRepository

class GetUserChallengeUseCase(
    private val repository: UserRepository,
) {
    suspend fun getUserChallenges(userId: Long) =
        repository.getUserChallenges(userId)
}
