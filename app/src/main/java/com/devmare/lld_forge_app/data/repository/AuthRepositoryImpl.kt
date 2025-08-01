package com.devmare.lld_forge_app.data.repository

import com.devmare.lld_forge_app.data.api.AuthApi
import com.devmare.lld_forge_app.data.model.LoginRequest
import com.devmare.lld_forge_app.data.model.LoginResponse
import com.devmare.lld_forge_app.data.model.SignupRequest
import com.devmare.lld_forge_app.data.model.SignupResponse
import com.devmare.lld_forge_app.domain.repository.AuthRepository

class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {

    override suspend fun signup(request: SignupRequest): SignupResponse {
        return api.signup(request)
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return api.login(request)
    }
}