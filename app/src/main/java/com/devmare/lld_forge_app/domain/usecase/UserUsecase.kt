package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.dto.ApiResponse
import com.devmare.lld_forge_app.domain.model.User
import com.devmare.lld_forge_app.domain.repository.UserRepository

class UserUsecase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): ApiResponse<User> {
        return userRepository.fetchUserProfile()
    }
}