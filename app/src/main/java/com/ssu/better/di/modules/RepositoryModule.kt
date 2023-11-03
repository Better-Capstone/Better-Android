package com.ssu.better.di.modules

import com.ssu.better.data.repositoryimpls.UserRepositoryImpl
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
}
