package com.devmare.lld_forge_app.core.interceptor

import android.util.Log
import com.devmare.lld_forge_app.data.api.AuthApi
import com.devmare.lld_forge_app.data.model.RefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val authApi: AuthApi,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val currentAccessToken = tokenProvider.getAccessToken()
            val refreshToken = tokenProvider.getRefreshToken()

            // If no refresh token available, don't retry
            if (refreshToken.isNullOrBlank()) return@runBlocking null

            // If token already refreshed in another thread, just retry with it
            val requestToken = response.request.header("Authorization")?.removePrefix("Bearer ")
            if (currentAccessToken != null && currentAccessToken != requestToken) {
                return@runBlocking response.request.newBuilder()
                    .header("Authorization", "Bearer $currentAccessToken")
                    .build()
            }

            try {
                val newTokenResponse = authApi.refresh(RefreshRequest(refreshToken))
                // Save new tokens
                tokenProvider.saveTokens(
                    newTokenResponse.data.data.accessToken,
                    newTokenResponse.data.data.refreshToken
                )

                // Retry the request with new access token
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newTokenResponse.data.data.accessToken}")
                    .build()
            } catch (e: Exception) {
                Log.e("TokenAuthenticator", "Error refreshing token: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
}
