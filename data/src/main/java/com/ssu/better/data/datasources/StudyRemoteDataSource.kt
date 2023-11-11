package com.ssu.better.data.datasources

import com.ssu.better.data.services.StudyService
import com.ssu.better.entity.study.StudyRequest
import retrofit2.Retrofit

class StudyRemoteDataSource(
    private val retrofitPair: Pair<Retrofit, Retrofit>,
) {
    private val publicStudyService = retrofitPair.first.create(StudyService::class.java)
    private val tokenStudyService = retrofitPair.second.create(StudyService::class.java)

    suspend fun createStudy(studyRequest: StudyRequest) =
        tokenStudyService.createStudy(studyRequest)

    suspend fun getStudyList() =
        publicStudyService.getStudyList()

    suspend fun getStudyListByUser(userId: Long) =
        publicStudyService.getStudyListByUser(userId)

    suspend fun getStudyListByCategory(categoryId: Long) =
        publicStudyService.getStudyListByCategory(categoryId)

    suspend fun getStudy(studyId: Long) =
        publicStudyService.getStudy(studyId)

    suspend fun joinStudy(studyId: Long) =
        tokenStudyService.joinStudy(studyId)

    suspend fun getStudyRank(studyId: Long) =
        publicStudyService.getStudyRank(studyId)

    suspend fun getGroupRankHistory(studyId: Long) =
        publicStudyService.getGroupRankHistory(studyId)
}