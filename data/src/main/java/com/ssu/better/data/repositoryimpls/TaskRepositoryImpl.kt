package com.ssu.better.data.repositoryimpls

import com.ssu.better.data.datasources.TaskRemoteDataSource
import com.ssu.better.data.util.CommonAPILogic
import com.ssu.better.domain.repository.TaskRepository
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.challenge.ChallengeRequest
import com.ssu.better.entity.task.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskRemoteDataSource: TaskRemoteDataSource,
) : TaskRepository {
    override suspend fun getTask(id: Long): Flow<Task> =
        CommonAPILogic.checkError(taskRemoteDataSource.getTask(id))

    override suspend fun postCreateTask(id: Long, challengeRequest: ChallengeRequest): Flow<Challenge> =
        CommonAPILogic.checkError(taskRemoteDataSource.postCreateChallenge(id, challengeRequest))

    override suspend fun createTask(request: TaskCreateRequest) = CommonAPILogic.checkError(taskRemoteDataSource.createTask(request))
}