package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.core.dto.LeaderboardDataDto
import com.devmare.lld_forge_app.domain.repository.UserRepository

class FetchLeaderboardMentorsUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): DefaultResponseDto<LeaderboardDataDto> {
        return userRepository.fetchLeaderboardMentors()
    }
}