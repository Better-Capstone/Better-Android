package com.ssu.better.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun getAuthToken(): Flow<String?>
    suspend fun setAuthToken(token: String)
}
