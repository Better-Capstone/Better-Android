package com.ssu.better.di.modules

import com.ssu.better.domain.repository.UserRepository
import com.ssu.better.domain.usecase.GetUserChallengeUseCase
import com.ssu.better.domain.usecase.GetUserRankUseCase
import com.ssu.better.domain.usecase.GetUserTasksUseCase
import com.ssu.better.domain.usecase.GetUserUseCase
import com.ssu.better.domain.usecase.PostUserLoginRequestUseCase
import com.ssu.better.domain.usecase.PostUserRegisterUseCase
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
}
