package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.data.model.MentorshipBookingDataWrapper
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.domain.repository.UserRepository
import javax.inject.Inject

class BookMentorshipSessionUsecase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(request: MentorshipBookingRequest): DefaultResponseDto<MentorshipBookingDataWrapper> {
        return repository.bookMentorshipSession(request)
    }
}