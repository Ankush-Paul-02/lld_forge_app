package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.dto.ApiResponse
import com.devmare.lld_forge_app.core.dto.MentorDto
import com.devmare.lld_forge_app.domain.repository.UserRepository

class FetchLeaderboardMentorsUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): ApiResponse<List<MentorDto>> {
        return userRepository.fetchLeaderboardMentors()
    }
}