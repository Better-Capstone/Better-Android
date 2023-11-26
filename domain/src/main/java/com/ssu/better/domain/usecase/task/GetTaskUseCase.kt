package com.ssu.better.domain.usecase.task

import com.ssu.better.domain.repository.TaskRepository

class GetTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend fun getTask(taskId: Long) = taskRepository.getTask(taskId)
}
