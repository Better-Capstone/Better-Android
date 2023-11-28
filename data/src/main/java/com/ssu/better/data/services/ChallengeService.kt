package com.ssu.better.data.services

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeVerifyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChallengeService {
    @GET("/challenge/{id}")
    suspend fun getChallenge(@Path("id") id: Long): Response<Challenge>

    @POST("/challenge/comment/{id}")
    suspend fun postChallengeComment(@Path("id") id: Long, @Body challengeVerifyRequest: ChallengeVerifyRequest): Response<Unit>
}
