package com.ssu.better.di.modules

import android.content.SharedPreferences
import com.ssu.better.BuildConfig
import com.ssu.better.di.qualifiers.ForAccessToken
import com.ssu.better.di.qualifiers.ForLogging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
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
    fun provideAccessTokenHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val accessToken = sharedPreferences.getString(SharedPreferenceModule.ACCESS_TOKEN, "")
        builder.addInterceptor(
            Interceptor { chain ->
                var request = chain.request()
                chain.proceed(
                    request.newBuilder().apply {
                        addHeader("token", accessToken ?: "")
                    }.build(),
                )
            },
        )

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }
}
