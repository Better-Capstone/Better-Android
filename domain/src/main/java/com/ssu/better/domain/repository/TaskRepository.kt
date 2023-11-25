package com.ssu.better.domain.repository

import com.ssu.better.entity.task.Task
import com.ssu.better.entity.task.TaskCreateRequest
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun createTask(request: TaskCreateRequest): Flow<Task>
}
