package com.ssu.better.data.repositoryimpls

import com.ssu.better.data.datasources.UserRemoteDataSource
import com.ssu.better.data.util.CommonAPILogic
import com.ssu.better.domain.repository.UserRepository
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserLoginRequest
import com.ssu.better.entity.user.UserLoginResponse
import com.ssu.better.entity.user.UserRank
import com.ssu.better.entity.user.UserRegisterRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun registerUser(userRegisterRequest: UserRegisterRequest): Flow<User> {
        return CommonAPILogic.checkError(userRemoteDataSource.registerUser(userRegisterRequest))
    }

    override suspend fun login(userLoginRequest: UserLoginRequest): Flow<UserLoginResponse> {
        return CommonAPILogic.checkError(userRemoteDataSource.login(userLoginRequest))
    }

    override suspend fun getUser(userId: Long): Flow<User> {
        return CommonAPILogic.checkError(userRemoteDataSource.getUser(userId))
    }

    override suspend fun getUserRank(userId: Long): Flow<UserRank> {
        return CommonAPILogic.checkError(userRemoteDataSource.getUserRank(userId))
    }

    override suspend fun getUserTasks(userId: Long): Flow<List<Task>> {
        return CommonAPILogic.checkError(userRemoteDataSource.getUserTasks(userId))
    }

    override suspend fun getUserChallenges(userId: Long): Flow<List<Challenge>> {
        return CommonAPILogic.checkError(userRemoteDataSource.getUserChallenges(userId))
    }

}
