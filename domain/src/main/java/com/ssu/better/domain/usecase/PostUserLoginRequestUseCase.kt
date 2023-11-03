package com.ssu.better.domain.usecase

import com.ssu.better.domain.repository.UserRepository
import com.ssu.better.entity.user.UserLoginRequest

class PostUserLoginRequestUseCase(
    private val repository: UserRepository,
) {
    suspend fun login(userLoginRequest: UserLoginRequest) =
        repository.login(userLoginRequest)
}
