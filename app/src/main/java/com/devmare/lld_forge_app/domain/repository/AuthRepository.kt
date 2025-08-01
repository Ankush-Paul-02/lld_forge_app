package com.devmare.lld_forge_app.domain.repository

import com.devmare.lld_forge_app.data.model.LoginRequest
import com.devmare.lld_forge_app.data.model.LoginResponse
import com.devmare.lld_forge_app.data.model.SignupRequest
import com.devmare.lld_forge_app.data.model.SignupResponse

interface AuthRepository {
    suspend fun signup(request: SignupRequest): SignupResponse

    suspend fun login(request: LoginRequest): LoginResponse
}