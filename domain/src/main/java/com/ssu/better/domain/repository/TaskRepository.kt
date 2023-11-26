package com.ssu.better.domain.repository

import com.ssu.better.entity.task.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTask(id: Long): Flow<Task>
}
