package com.ssu.better.data.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ssu.better.entity.user.UserPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPrefManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun updateUserId(id: Long) {
        dataStore.edit { pref ->
            pref[PreferencesKeys.KEY_USER_ID] = id
        }
    }

    suspend fun updateNickName(nickname: String) {
        dataStore.edit { pref ->
            pref[PreferencesKeys.KEY_NICKNAME] = nickname
        }
    }

    fun getUserPref(): Flow<UserPref?> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val userId = preferences[PreferencesKeys.KEY_USER_ID]
        val nickname = preferences[PreferencesKeys.KEY_NICKNAME]
        val rank = preferences[PreferencesKeys.KEY_USER_RANK]
        val score = preferences[PreferencesKeys.KEY_USER_SCORE]

        UserPref(
            id = userId ?: return@map null,
            nickname = nickname ?: return@map null,
            rank = rank ?: 0,
            score = score ?: 0,
        )
    }


    private object PreferencesKeys {
        val KEY_USER_ID = longPreferencesKey("KEY_USER_ID")
        val KEY_NICKNAME = stringPreferencesKey("KEY_NICKNAME")
        val KEY_USER_RANK = intPreferencesKey("KEY_USER_RNAK")
        val KEY_USER_SCORE = intPreferencesKey("KEY_USER_SCORE")
    }

}
