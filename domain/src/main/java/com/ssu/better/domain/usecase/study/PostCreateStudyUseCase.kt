package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository
import com.ssu.better.entity.study.StudyRequest

class PostCreateStudyUseCase(
    private val repository: StudyRepository,
) {
    suspend fun createStudy(studyRequest: StudyRequest) =
        repository.createStudy(studyRequest)
}
