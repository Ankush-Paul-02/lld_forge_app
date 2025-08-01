package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.data.model.UserResponse
import com.devmare.lld_forge_app.domain.repository.UserRepository

class UserUsecase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): UserResponse {
        return userRepository.fetechUserProfile()
    }
}