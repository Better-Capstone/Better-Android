package com.ssu.better.di.modules

import com.ssu.better.data.util.EnumConverterFactory
import com.ssu.better.di.qualifiers.ForAccessToken
import com.ssu.better.di.qualifiers.ForLogging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    private const val BASE_URL = "http://13.124.213.113:8080"

    @Provides
    fun provideRetrofitPair(
        @ForLogging loggingOkHttpClient: OkHttpClient,
        @ForAccessToken accessTokenHttpClient: OkHttpClient,
    ): Pair<Retrofit, Retrofit> {
        val publicRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .client(loggingOkHttpClient)
            .build()

        val authRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .client(accessTokenHttpClient)
            .build()

        return Pair(publicRetrofit, authRetrofit)
    }
}
