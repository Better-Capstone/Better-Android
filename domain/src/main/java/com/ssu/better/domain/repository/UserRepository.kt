package com.ssu.better.domain.repository

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.task.UserTask
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserCheck
import com.ssu.better.entity.user.UserLoginResponse
import com.ssu.better.entity.user.UserRank
import com.ssu.better.entity.user.UserRankHistory
import com.ssu.better.entity.user.UserRegisterRequest
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(userRegisterRequest: UserRegisterRequest): Flow<UserLoginResponse>

    suspend fun login(kakaoToken: String): Flow<UserLoginResponse>

    suspend fun getUser(userId: Long): Flow<User>

    suspend fun getUserRank(userId: Long): Flow<UserRank>

    suspend fun getUserTasks(userId: Long): Flow<List<UserTask>>

    suspend fun getUserChallenges(userId: Long): Flow<List<Challenge>>

    suspend fun getUserCheck(userId: Long): Flow<UserCheck>

    suspend fun getUserRankHistory(userId: Long): Flow<List<UserRankHistory>>
}
