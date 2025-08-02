package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.interceptor.TokenProvider
import com.devmare.lld_forge_app.data.api.AuthApi
import com.devmare.lld_forge_app.data.model.RefreshRequest
import jakarta.inject.Inject

class EnsureValidTokenUseCase @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val authApi: AuthApi,
) {
    suspend fun execute(): Boolean {
        val isExpired = tokenProvider.isAccessTokenExpired()
        if (!isExpired) return true

        val refreshToken = tokenProvider.getRefreshToken() ?: return false

        return try {
            val newTokens = authApi.refresh(RefreshRequest(refreshToken))
            tokenProvider.saveTokens(
                newTokens.data.data.accessToken,
                newTokens.data.data.refreshToken
            )
            true
        } catch (e: Exception) {
            false
        }
    }
}
