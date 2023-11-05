package com.ssu.better.data.repositoryimpls

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ssu.better.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(val dataStore: DataStore<Preferences>) : DataStoreRepository {

    companion object{
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    override suspend fun getAuthToken(): Flow<String?> {
        return dataStore.data.catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }.map { it[AUTH_TOKEN] ?: null}
    }

    override suspend fun setAuthToken(token: String) {
        dataStore.edit { it[AUTH_TOKEN] = token }
    }
}
