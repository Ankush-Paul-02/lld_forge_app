package com.devmare.lld_forge_app.data.api

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.data.model.LoginRequest
import com.devmare.lld_forge_app.data.model.LoginResponse
import com.devmare.lld_forge_app.data.model.RefreshRequest
import com.devmare.lld_forge_app.data.model.SignupRequest
import com.devmare.lld_forge_app.data.model.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): SignupResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/refresh-token")
    suspend fun refresh(@Body request: RefreshRequest): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): DefaultResponseDto
}