package com.ssu.better.di.modules

import com.ssu.better.data.datasources.StudyRemoteDataSource
import com.ssu.better.data.datasources.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun providesUserRemoteDataSource(retrofitPair: Pair<Retrofit, Retrofit>): UserRemoteDataSource {
        return UserRemoteDataSource(retrofitPair)
    }

    @Provides
    @Singleton
    fun providesStudyRemoteDataSource(retrofitPair: Pair<Retrofit, Retrofit>): StudyRemoteDataSource {
        return StudyRemoteDataSource(retrofitPair)
    }
}
