package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyRankUseCase(
    private val studyRepository: StudyRepository
) {
    suspend fun getStudyRank(studyId: Long) =
        studyRepository.getStudyRank(studyId)
}
