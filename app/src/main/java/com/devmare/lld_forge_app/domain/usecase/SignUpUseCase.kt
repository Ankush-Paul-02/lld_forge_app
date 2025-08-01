package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.data.model.SignupRequest
import com.devmare.lld_forge_app.data.model.SignupResponse
import com.devmare.lld_forge_app.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(result: SignupRequest): SignupResponse {
        return repository.signup(result)
    }
}