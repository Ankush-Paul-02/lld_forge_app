package com.devmare.lld_forge_app.domain.usecase

import com.devmare.lld_forge_app.core.dto.ApiResponse
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.data.model.MentorshipBookingResponse
import com.devmare.lld_forge_app.domain.repository.UserRepository
import javax.inject.Inject

class BookMentorshipSessionUsecase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(request: MentorshipBookingRequest): ApiResponse<MentorshipBookingResponse> {
        return repository.bookMentorshipSession(request)
    }
}