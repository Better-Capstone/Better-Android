package com.ssu.better.domain.usecase.study

import com.ssu.better.domain.repository.StudyRepository

class GetStudyListByCategoryUseCase(
    private val studyRepository: StudyRepository,
) {
    suspend fun getStudyListByCategory(categoryId: Long) =
        studyRepository.getStudyListByCategory(categoryId)
}
