package com.ssu.better.data.datasources

import com.ssu.better.data.services.UserService
import com.ssu.better.entity.user.UserRegisterRequest
import retrofit2.Retrofit

class UserRemoteDataSource(
    private val retrofitPair: Pair<Retrofit, Retrofit>,
) {
    private val publicUserService = retrofitPair.first.create(UserService::class.java)
    private val tokenUserService = retrofitPair.second.create(UserService::class.java)

    suspend fun registerUser(userRegisterRequest: UserRegisterRequest) =
        publicUserService.registerUser(userRegisterRequest)

    suspend fun login(kakaoToken: String) =
        publicUserService.login(kakaoToken)

    suspend fun getUser(userId: Long) =
        tokenUserService.getUser(userId)

    suspend fun getUserRank(userId: Long) =
        tokenUserService.getUserRank(userId)

    suspend fun getUserTasks(userId: Long) =
        tokenUserService.getUserTasks(userId)

    suspend fun getUserChallenges(userId: Long) =
        tokenUserService.getUserChallenges(userId)

    suspend fun getUserCheck(userId: Long) =
        publicUserService.getUserCheck(userId)

    suspend fun getUserRankHistory(userId: Long) =
        tokenUserService.getUserRankHistory(userId)
}
