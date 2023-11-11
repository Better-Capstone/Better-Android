package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class PostJoinStudyUseCase(
    private val studyRepository: StudyRepository
) {
    suspend fun joinStudy(studyId: Long) =
        studyRepository.joinStudy(studyId)
}
