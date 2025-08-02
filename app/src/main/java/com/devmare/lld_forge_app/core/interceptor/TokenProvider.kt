package com.devmare.lld_forge_app.core.interceptor

interface TokenProvider {
    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun saveTokens(accessToken: String, refreshToken: String)

    suspend fun saveToken(token: String)

    suspend fun deleteToken()

    suspend fun isAccessTokenExpired(): Boolean
}