package com.ssu.better.data.repositoryimpls

import com.ssu.better.data.datasources.ChallengeRemoteDataSource
import com.ssu.better.data.util.CommonAPILogic
import com.ssu.better.domain.repository.ChallengeRepository
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeVerifyRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val remoteDataSource: ChallengeRemoteDataSource,
) : ChallengeRepository {
    override suspend fun getChallenge(id: Long): Flow<Challenge> =
        CommonAPILogic.checkError(remoteDataSource.getChallenge(id))
    override suspend fun postChallengeComment(id: Long, challengeVerifyRequest: ChallengeVerifyRequest): Flow<Unit> =
        CommonAPILogic.checkError(remoteDataSource.postChallengeComment(id, challengeVerifyRequest))
}
