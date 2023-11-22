package com.ssu.better.data.services

import com.ssu.better.entity.study.GroupRank
import com.ssu.better.entity.study.GroupRankHistory
import com.ssu.better.entity.study.Study
import com.ssu.better.entity.study.StudyRequest
import com.ssu.better.entity.study.StudyUser
import com.ssu.better.entity.task.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudyService {
    @POST("/study/create")
    suspend fun createStudy(@Body studyRequest: StudyRequest): Response<Study>

    @GET("/study")
    suspend fun getStudyList(): Response<ArrayList<Study>>

    @GET("/study/user/{userId}")
    suspend fun getStudyListByUser(@Path("userId") userId: Long): Response<ArrayList<Study>>

    @GET("/study/category/{categoryId}")
    suspend fun getStudyListByCategory(@Path("categoryId") categoryId: Long): Response<ArrayList<Study>>

    @GET("/study/{studyId}")
    suspend fun getStudy(@Path("studyId") studyId: Long): Response<Study>

    @GET("/study/{studyId}/users")
    suspend fun getStudyUserList(@Path("studyId") studyId: Long): Response<ArrayList<StudyUser>>

    @GET("/study/{studyId}/tasks")
    suspend fun getStudyTaskList(@Path("studyId") studyId: Long): Response<ArrayList<Task>>

    @POST("/study/{studyId}/join")
    suspend fun joinStudy(@Path("studyId") studyId: Long): Response<Unit>

    @GET("/study/{studyId}/rank")
    suspend fun getStudyRank(@Path("studyId") studyId: Long): Response<GroupRank>

    @GET("/study/{studyId}/report/history")
    suspend fun getGroupRankHistory(@Path("studyId") studyId: Long): Response<ArrayList<GroupRankHistory>>
}
