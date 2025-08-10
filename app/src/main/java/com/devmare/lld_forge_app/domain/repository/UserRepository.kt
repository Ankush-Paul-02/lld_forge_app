package com.devmare.lld_forge_app.domain.repository

import com.devmare.lld_forge_app.core.dto.ApiResponse
import com.devmare.lld_forge_app.core.dto.MentorDto
import com.devmare.lld_forge_app.core.dto.MentorshipSessionResponseDto
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.data.model.MentorshipBookingResponse
import com.devmare.lld_forge_app.domain.model.User

interface UserRepository {

    suspend fun fetchUserProfile(): ApiResponse<User>

    suspend fun fetchLeaderboardMentors(): ApiResponse<List<MentorDto>>

    suspend fun bookMentorshipSession(mentorshipBookingRequest: MentorshipBookingRequest): ApiResponse<MentorshipBookingResponse>

    suspend fun fetchMentorshipSessions(): ApiResponse<List<MentorshipSessionResponseDto>>
}