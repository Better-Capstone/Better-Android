package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyTaskListUseCase(
    private val studyRepository: StudyRepository,
) {
    suspend fun getStudyTaskList(studyId: Long) =
        studyRepository.getStudyTaskList(studyId)
}
