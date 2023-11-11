package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyListUseCase(
    private val studyRepository: StudyRepository
) {
    suspend fun getStudyList() =
        studyRepository.getStudyList()
}
