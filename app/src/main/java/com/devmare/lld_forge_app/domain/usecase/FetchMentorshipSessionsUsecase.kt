package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.dto.ApiResponse
import com.devmare.lld_forge_app.core.dto.MentorshipSessionResponseDto
import com.devmare.lld_forge_app.domain.repository.UserRepository
import jakarta.inject.Inject

class FetchMentorshipSessionsUsecase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(): ApiResponse<List<MentorshipSessionResponseDto>> {
        return repository.fetchMentorshipSessions()
    }
}