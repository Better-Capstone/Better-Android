package com.ssu.better.domain.repository

import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.GroupRankHistory
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyRequest
import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    suspend fun createStudy(studyRequest: StudyRequest): Flow<Study>

    suspend fun getStudyList(): Flow<ArrayList<Study>>

    suspend fun getStudyListByUser(userId: Long): Flow<ArrayList<Study>>

    suspend fun getStudyListByCategory(categoryId: Long): Flow<ArrayList<Study>>

    suspend fun getStudy(studyId: Long): Flow<Study>

    suspend fun joinStudy(studyId: Long): Flow<Unit>

    suspend fun getStudyRank(studyId: Long): Flow<GroupRank>

    suspend fun getGroupRankHistory(studyId: Long): Flow<ArrayList<GroupRankHistory>>
}
