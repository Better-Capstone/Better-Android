package com.ssu.better.domain.usecase

import com.ssu.better.domain.repository.UserRepository

class GetUserTasksUseCase(
    private val repository: UserRepository,
) {
    suspend fun getUserTasks(userId: Long) = repository.getUserTasks(userId)
}
