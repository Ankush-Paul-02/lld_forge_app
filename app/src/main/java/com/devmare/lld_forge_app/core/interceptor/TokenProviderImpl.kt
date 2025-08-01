package com.devmare.lld_forge_app.core.interceptor

import com.devmare.lld_forge_app.core.prefs.DataStoreManager
import jakarta.inject.Inject

class TokenProviderImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : TokenProvider {
    override suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken()
    }
}