package com.ssu.better.domain.usecase.user

import com.ssu.better.domain.repository.UserRepository

class GetUserRankHistoryUseCase(
    private val repository: UserRepository,
) {
    suspend fun getUserRankHistory(id: Long) =
        repository.getUserRankHistory(id)
}
