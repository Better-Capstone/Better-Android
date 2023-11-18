package com.ssu.better.domain.usecase.user

import com.ssu.better.domain.repository.UserRepository

class PostUserLoginRequestUseCase(
    private val repository: UserRepository,
) {
    suspend fun login(kakaoToken: String) =
        repository.login(kakaoToken)

    suspend fun userCheck(userId: Long) = repository.getUserCheck(userId)
}
