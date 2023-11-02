package com.ssu.better.domain.usecase

import com.ssu.better.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository,
) {
    suspend fun getUser(userId: Long) =
        repository.getUser(userId)
}
