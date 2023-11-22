package com.ssu.better.di.modules

import com.ssu.better.BuildConfig
import com.ssu.better.data.datasources.TokenManager
import com.ssu.better.di.qualifiers.ForAccessToken
import com.ssu.better.di.qualifiers.ForLogging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    private const val AUTHORIZATION = "Authorization"

    @Provides
    @Singleton
    @ForLogging
    fun provideLoggingHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // log
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @ForAccessToken
    fun provideAccessTokenHttpClient(tokenManager: TokenManager): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(
            AccessTokenInterceptor(tokenManager),
        )

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }
}
