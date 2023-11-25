package com.ssu.better.data.repositoryimpls

import com.ssu.better.data.datasources.TaskRemoteDataSource
import com.ssu.better.data.util.CommonAPILogic
import com.ssu.better.domain.repository.TaskRepository
import com.ssu.better.entity.task.TaskCreateRequest
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskRemoteDataSource: TaskRemoteDataSource,
) : TaskRepository {
    override suspend fun createTask(request: TaskCreateRequest) = CommonAPILogic.checkError(taskRemoteDataSource.createTask(request))
}
