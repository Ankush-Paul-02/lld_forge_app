package com.devmare.lld_forge_app.core.interceptor

import com.devmare.lld_forge_app.core.prefs.DataStoreManager
import jakarta.inject.Inject
import org.json.JSONObject

class TokenProviderImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : TokenProvider {
    override suspend fun getAccessToken(): String? {
        return dataStoreManager.getAccessToken()
    }

    override suspend fun getRefreshToken(): String? {
        return dataStoreManager.getRefreshToken()
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStoreManager.saveAccessToken(accessToken)
        dataStoreManager.saveRefreshToken(refreshToken)
    }

    override suspend fun saveToken(token: String) {
        dataStoreManager.saveAccessToken(token)
    }

    override suspend fun deleteToken() {
        dataStoreManager.clearAccessToken()
    }

    override suspend fun isAccessTokenExpired(): Boolean {
        val token = dataStoreManager.getAccessToken()
        val parts = token?.split(".")
        if (parts?.size != 3) return true

        return try {
            val payload =
                String(android.util.Base64.decode(parts?.get(1), android.util.Base64.DEFAULT))
            val exp = JSONObject(payload).getLong("exp")
            val currentTime = System.currentTimeMillis() / 1000
            currentTime >= exp
        } catch (e: Exception) {
            true
        }
    }
}