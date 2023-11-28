package com.ssu.better.data.datasources

import com.ssu.better.data.services.ChallengeService
import com.ssu.better.entity.challenge.ChallengeVerifyRequest
import retrofit2.Retrofit

class ChallengeRemoteDataSource(
    retrofitPair: Pair<Retrofit, Retrofit>,
) {
    private val tokenChallengeService = retrofitPair.second.create(ChallengeService::class.java)

    suspend fun getChallenge(id: Long) =
        tokenChallengeService.getChallenge(id)

    suspend fun postChallengeComment(id: Long, challengeVerifyRequest: ChallengeVerifyRequest) =
        tokenChallengeService.postChallengeComment(id, challengeVerifyRequest)
}
