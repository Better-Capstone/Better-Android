package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyListByQueryUseCase(
    private val studyRepository: StudyRepository,
) {
    suspend fun getStudyListByQuery(keyword: String, categoryId: Int?) =
        studyRepository.getStudyListByQuery(keyword, categoryId)
}
