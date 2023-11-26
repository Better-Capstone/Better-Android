package com.ssu.better.data.repositoryimpls

import com.ssu.better.data.datasources.StudyRemoteDataSource
import com.ssu.better.data.util.CommonAPILogic
import com.ssu.better.domain.repository.StudyRepository
import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.GroupRankHistory
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyRequest
import com.ssu.better.entity.study.StudyUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudyRepositoryImpl @Inject constructor(
    private val studyRemoteDataSource: StudyRemoteDataSource,
) : StudyRepository {
    override suspend fun createStudy(studyRequest: StudyRequest): Flow<Study> =
        CommonAPILogic.checkError(studyRemoteDataSource.createStudy(studyRequest))

    override suspend fun getStudyList(): Flow<ArrayList<Study>> =
        CommonAPILogic.checkError(studyRemoteDataSource.getStudyList())

    override suspend fun getStudyListByUser(userId: Long): Flow<ArrayList<Study>> =
        CommonAPILogic.checkError(studyRemoteDataSource.getStudyListByUser(userId))

    override suspend fun getStudyListByCategory(categoryId: Long): Flow<ArrayList<Study>> =
        CommonAPILogic.checkError(studyRemoteDataSource.getStudyListByCategory(categoryId))

    override suspend fun getStudyUserList(studyId: Long): Flow<ArrayList<StudyUser>> =
        CommonAPILogic.checkError(studyRemoteDataSource.getStudyUserList(studyId))

    override suspend fun getStudy(studyId: Long): Flow<Study> =
        CommonAPILogic.checkError(studyRemoteDataSource.getStudy(studyId))

    override suspend fun joinStudy(studyId: Long): Flow<Unit> =
        CommonAPILogic.checkError(studyRemoteDataSource.joinStudy(studyId))

    override suspend fun getStudyRank(studyId: Long): Flow<GroupRank> =
        CommonAPILogic.checkError(studyRemoteDataSource.getStudyRank(studyId))

    override suspend fun getGroupRankHistory(studyId: Long): Flow<ArrayList<GroupRankHistory>> =
        CommonAPILogic.checkError(studyRemoteDataSource.getGroupRankHistory(studyId))

    override suspend fun getStudyListByQuery(keyword: String, categoryId: Int?): Flow<ArrayList<Study>> = CommonAPILogic.checkError(
        studyRemoteDataSource
            .getStudyQueryList(keyword, categoryId),
    )
}
