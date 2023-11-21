package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyUserListUseCase(
    private val studyRepository: StudyRepository,
) {
    suspend fun getStudyUserList(studyId: Long) =
        studyRepository.getStudyUserList(studyId)
}
