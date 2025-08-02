package com.devmare.lld_forge_app.core.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    suspend fun saveTokens(token: String, refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun getAccessToken(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[ACCESS_TOKEN_KEY]
    }

    suspend fun getRefreshToken(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[REFRESH_TOKEN_KEY]
    }

    suspend fun clearAccessToken() {
        context.dataStore.edit {
            it.clear()
        }
    }
}