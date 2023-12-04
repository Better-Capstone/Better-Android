package com.ssu.better.di.modules

import com.ssu.better.data.repositoryimpls.ChallengeRepositoryImpl
import com.ssu.better.data.repositoryimpls.StudyRepositoryImpl
import com.ssu.better.data.repositoryimpls.TaskRepositoryImpl
import com.ssu.better.data.repositoryimpls.UserRepositoryImpl
import com.ssu.better.domain.repository.ChallengeRepository
import com.ssu.better.domain.repository.StudyRepository
import com.ssu.better.domain.repository.TaskRepository
import com.ssu.better.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesUserRepository(repository: UserRepositoryImpl): UserRepository {
        return repository
    }

    @Provides
    @Singleton
    fun providesStudyRepository(repository: StudyRepositoryImpl): StudyRepository {
        return repository
    }

    @Provides
    @Singleton
    fun providesTaskRepository(repository: TaskRepositoryImpl): TaskRepository {
        return repository
    }

    @Provides
    @Singleton
    fun providesChallengeRepository(repository: ChallengeRepositoryImpl): ChallengeRepository {
        return repository
    }
}
