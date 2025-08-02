package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.core.interceptor.TokenProvider
import com.devmare.lld_forge_app.data.api.AuthApi
import jakarta.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authApi: AuthApi,
    private val tokenProvider: TokenProvider
) {
    suspend fun execute(): Boolean {
        return try {
            val response = authApi.logout()
            if (response.status == DefaultResponseDto.Status.SUCCESS) {
                tokenProvider.deleteToken()
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }
}
