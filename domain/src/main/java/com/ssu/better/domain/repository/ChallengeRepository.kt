package com.ssu.better.domain.repository

import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeVerifyRequest
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getChallenge(id: Long): Flow<Challenge>

    suspend fun postChallengeComment(id: Long, challengeVerifyRequest: ChallengeVerifyRequest): Flow<Unit>
}
