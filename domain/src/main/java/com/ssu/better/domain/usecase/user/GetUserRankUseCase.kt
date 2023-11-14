package com.ssu.better.domain.usecase.user

import com.ssu.better.domain.repository.UserRepository

class GetUserRankUseCase(
    private val repository: UserRepository,
) {
    suspend fun getUserRank(userId: Long) =
        repository.getUserRank(userId)
}
