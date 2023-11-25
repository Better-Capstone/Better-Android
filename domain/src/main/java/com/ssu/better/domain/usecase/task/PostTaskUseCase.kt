package com.ssu.better.domain.usecase.task

import com.ssu.better.domain.repository.TaskRepository
import com.ssu.better.entity.task.TaskCreateRequest
import javax.inject.Inject

class PostTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend fun createTask(request: TaskCreateRequest) = taskRepository.createTask(request)
}
