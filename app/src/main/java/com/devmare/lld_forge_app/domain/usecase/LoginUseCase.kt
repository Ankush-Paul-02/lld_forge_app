package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.data.model.LoginRequest
import com.devmare.lld_forge_app.data.model.LoginResponse
import com.devmare.lld_forge_app.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(result: LoginRequest): LoginResponse {
        return repository.login(result)
    }
}