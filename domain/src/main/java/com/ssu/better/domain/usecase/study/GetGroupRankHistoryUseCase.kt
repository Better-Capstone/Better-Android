package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetGroupRankHistoryUseCase(
    private val studyRepository: StudyRepository
) {
    suspend fun getGroupRankHistory(studyId: Long) =
        studyRepository.getGroupRankHistory(studyId)
}
