package com.devmare.lld_forge_app.data.repository

import com.devmare.lld_forge_app.core.dto.ApiResponse
import com.devmare.lld_forge_app.core.dto.MentorDto
import com.devmare.lld_forge_app.core.dto.MentorshipSessionResponseDto
import com.devmare.lld_forge_app.data.api.UserApi
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.data.model.MentorshipBookingResponse
import com.devmare.lld_forge_app.domain.model.User
import com.devmare.lld_forge_app.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override suspend fun fetchUserProfile(): ApiResponse<User> {
        return userApi.fetchUserProfile()
    }

    override suspend fun fetchLeaderboardMentors(): ApiResponse<List<MentorDto>> {
        return userApi.fetchLeaderboardMentors()
    }

    override suspend fun bookMentorshipSession(mentorshipBookingRequest: MentorshipBookingRequest): ApiResponse<MentorshipBookingResponse> {
        return userApi.bookMentorshipSession(mentorshipBookingRequest)
    }

    override suspend fun fetchMentorshipSessions(): ApiResponse<List<MentorshipSessionResponseDto>> {
        return userApi.fetchMentorshipSessions()
    }
}