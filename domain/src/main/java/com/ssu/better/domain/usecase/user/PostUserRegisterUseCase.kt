package com.ssu.better.domain.usecase.user

import com.ssu.better.domain.repository.UserRepository
import com.ssu.better.entity.user.UserRegisterRequest

class PostUserRegisterUseCase(
    private val repository: UserRepository,
) {
    suspend fun registerUser(userRegisterRequest: UserRegisterRequest) = repository.registerUser(userRegisterRequest)
}
