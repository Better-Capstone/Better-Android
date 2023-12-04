package com.ssu.better.data.services

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeRequest
import com.ssu.better.entity.task.Task
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface TaskService {
    @GET("/task/{id}")
    suspend fun getTask(@Path("id") taskId: Long): Response<Task>

    @Multipart
    @POST("/task/{id}/challenge/register")
    suspend fun postCreateChallenge(
        @Path("id") taskId: Long,
        @Part image: MultipartBody.Part,
        @Part("request") challengeRequest: ChallengeRequest,
    ): Response<Challenge>
}
