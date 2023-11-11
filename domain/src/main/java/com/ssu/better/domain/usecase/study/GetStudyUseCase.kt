package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyUseCase(
    private val studyRepository: StudyRepository,
) {
    suspend fun getStudy(studyId: Long) =
        studyRepository.getStudy(studyId)
}
