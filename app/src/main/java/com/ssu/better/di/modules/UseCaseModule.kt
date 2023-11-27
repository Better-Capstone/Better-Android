package com.ssu.better.di.modules

import com.ssu.better.domain.repository.StudyRepository
import com.ssu.better.domain.repository.TaskRepository
import com.ssu.better.domain.repository.UserRepository
import com.ssu.better.domain.usecase.study.GetGroupRankHistoryUseCase
import com.ssu.better.domain.usecase.study.GetStudyListByCategoryUseCase
import com.ssu.better.domain.usecase.study.GetStudyListByQueryUseCase
import com.ssu.better.domain.usecase.study.GetStudyListByUserUseCase
import com.ssu.better.domain.usecase.study.GetStudyListUseCase
import com.ssu.better.domain.usecase.study.GetStudyTaskListUseCase
import com.ssu.better.domain.usecase.study.GetStudyUseCase
import com.ssu.better.domain.usecase.study.GetStudyUserListUseCase
import com.ssu.better.domain.usecase.study.PostCreateStudyUseCase
import com.ssu.better.domain.usecase.study.PostJoinStudyUseCase
import com.ssu.better.domain.usecase.task.GetTaskUseCase
import com.ssu.better.domain.usecase.task.PostCreateChallengeUseCase
import com.ssu.better.domain.usecase.user.GetUserChallengeUseCase
import com.ssu.better.domain.usecase.user.GetUserRankUseCase
import com.ssu.better.domain.usecase.user.GetUserTasksUseCase
import com.ssu.better.domain.usecase.user.GetUserUseCase
import com.ssu.better.domain.usecase.user.PostUserLoginRequestUseCase
import com.ssu.better.domain.usecase.user.PostUserRegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesGetUserChallengeUseCase(repository: UserRepository): GetUserChallengeUseCase {
        return GetUserChallengeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetUserRankUseCase(repository: UserRepository): GetUserRankUseCase {
        return GetUserRankUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetUserTaskUseCase(repository: UserRepository): GetUserTasksUseCase {
        return GetUserTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetUserUseCase(repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesPostLoginRequestUseCase(repository: UserRepository): PostUserLoginRequestUseCase {
        return PostUserLoginRequestUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesPostUserRegisterUseCase(repository: UserRepository): PostUserRegisterUseCase {
        return PostUserRegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetGroupRankHistoryUseCase(repository: StudyRepository): GetGroupRankHistoryUseCase {
        return GetGroupRankHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetStudyListByCategoryUseCase(repository: StudyRepository): GetStudyListByCategoryUseCase {
        return GetStudyListByCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetStudyListByUserUseCase(repository: StudyRepository): GetStudyListByUserUseCase {
        return GetStudyListByUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetStudyListUseCase(repository: StudyRepository): GetStudyListUseCase {
        return GetStudyListUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetStudyUseCase(repository: StudyRepository): GetStudyUseCase {
        return GetStudyUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesPostCreateStudyUseCase(repository: StudyRepository): PostCreateStudyUseCase {
        return PostCreateStudyUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesPostJoinStudyUseCase(repository: StudyRepository): PostJoinStudyUseCase {
        return PostJoinStudyUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetStudyUserListUseCase(repository: StudyRepository): GetStudyUserListUseCase {
        return GetStudyUserListUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetStudyTaskListUseCase(repository: StudyRepository): GetStudyTaskListUseCase {
        return GetStudyTaskListUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetStudyListByQueryUseCase(repository: StudyRepository): GetStudyListByQueryUseCase {
        return GetStudyListByQueryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetTaskUseCase(repository: TaskRepository): GetTaskUseCase {
        return GetTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesPostCreateChallengeUseCase(repository: TaskRepository): PostCreateChallengeUseCase {
        return PostCreateChallengeUseCase(repository)
    }
}
