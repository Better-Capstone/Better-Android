package com.ssu.better.data.services

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.task.Task
import com.ssu.better.entity.user.User
import com.ssu.better.entity.user.UserCheck
import com.ssu.better.entity.user.UserLoginResponse
import com.ssu.better.entity.user.UserRank
import com.ssu.better.entity.user.UserRegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @POST("/user/register")
    suspend fun registerUser(@Body userRegisterRequest: UserRegisterRequest): Response<UserLoginResponse>

    @POST("/user/login")
    suspend fun login(@Query("kakaoToken") kakaoToken: String): Response<UserLoginResponse>

    @GET("/user/{id}")
    suspend fun getUser(@Path("id") userId: Long): Response<User>

    @GET("/user/{id}/rank")
    suspend fun getUserRank(@Path("id") userId: Long): Response<UserRank>

    @GET("/user/{id}/tasks")
    suspend fun getUserTasks(@Path("id") userId: Long): Response<List<Task>>

    @GET("/user/{id}/challenges")
    suspend fun getUserChallenges(@Path("id") userId: Long): Response<List<Challenge>>

    @GET("/user/check/{id}")
    suspend fun getUserCheck(@Path("id") userId: Long): Response<UserCheck>
}
