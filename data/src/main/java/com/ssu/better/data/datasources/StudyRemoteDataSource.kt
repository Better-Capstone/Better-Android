package com.ssu.better.data.datasources

import com.ssu.better.data.services.StudyService
import com.ssu.better.entity.study.StudyRequest
import retrofit2.Retrofit

class StudyRemoteDataSource(
    private val retrofitPair: Pair<Retrofit, Retrofit>,
) {

    private val tokenStudyService = retrofitPair.second.create(StudyService::class.java)

    suspend fun createStudy(studyRequest: StudyRequest) = tokenStudyService.createStudy(studyRequest)

    suspend fun getStudyList() = tokenStudyService.getStudyList()

    suspend fun getStudyListByUser(userId: Long) = tokenStudyService.getStudyListByUser(userId)

    suspend fun getStudyListByCategory(categoryId: Long) = tokenStudyService.getStudyListByCategory(categoryId)

    suspend fun getStudy(studyId: Long) = tokenStudyService.getStudy(studyId)

    suspend fun joinStudy(studyId: Long) = tokenStudyService.joinStudy(studyId)

    suspend fun getStudyRank(studyId: Long) = tokenStudyService.getStudyRank(studyId)

    suspend fun getGroupRankHistory(studyId: Long) = tokenStudyService.getGroupRankHistory(studyId)

    suspend fun getStudyTaskList(studyId: Long) = tokenStudyService.getStudyTaskList(studyId)

    suspend fun getStudyUserList(studyId: Long) = tokenStudyService.getStudyUserList(studyId)

    suspend fun getStudyQueryList(keyword: String, categoryId: Int?) = tokenStudyService.getStudyListByQuery(keyword, categoryId)
}
