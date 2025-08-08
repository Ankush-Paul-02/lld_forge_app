package com.devmare.lld_forge_app.domain.repository

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.core.dto.LeaderboardDataDto
import com.devmare.lld_forge_app.data.model.MentorshipBookingDataWrapper
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.data.model.UserResponse

interface UserRepository {

    suspend fun fetechUserProfile(): UserResponse

    suspend fun fetchLeaderboardMentors(): DefaultResponseDto<LeaderboardDataDto>

    suspend fun bookMentorshipSession(mentorshipBookingRequest: MentorshipBookingRequest): DefaultResponseDto<MentorshipBookingDataWrapper>
}