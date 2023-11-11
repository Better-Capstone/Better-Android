package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyListByUserUseCase(
    private val studyRepository: StudyRepository
) {
    suspend fun getStudyListByUser(userId: Long) =
        studyRepository.getStudyListByUser(userId)
}
