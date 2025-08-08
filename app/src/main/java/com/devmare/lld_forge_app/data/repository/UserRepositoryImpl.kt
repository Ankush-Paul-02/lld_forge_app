package com.devmare.lld_forge_app.data.repository

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.core.dto.LeaderboardDataDto
import com.devmare.lld_forge_app.data.api.UserApi
import com.devmare.lld_forge_app.data.model.MentorshipBookingDataWrapper
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.data.model.UserResponse
import com.devmare.lld_forge_app.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override suspend fun fetechUserProfile(): UserResponse {
        return userApi.fetechUserProfile()
    }

    override suspend fun fetchLeaderboardMentors(): DefaultResponseDto<LeaderboardDataDto> {
        return userApi.fetchLeaderboardMentors()
    }

    override suspend fun bookMentorshipSession(mentorshipBookingRequest: MentorshipBookingRequest): DefaultResponseDto<MentorshipBookingDataWrapper> {
        return userApi.bookMentorshipSession(mentorshipBookingRequest)
    }
}