package com.devmare.lld_forge_app.data.api

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.core.dto.LeaderboardDataDto
import com.devmare.lld_forge_app.data.model.MentorshipBookingDataWrapper
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.data.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("users/me")
    suspend fun fetechUserProfile(): UserResponse

    @GET("users/leaderboard/mentors")
    suspend fun fetchLeaderboardMentors(): DefaultResponseDto<LeaderboardDataDto>

    @POST("users/mentorship-session/book")
    suspend fun bookMentorshipSession(
        @Body request: MentorshipBookingRequest,
    ): DefaultResponseDto<MentorshipBookingDataWrapper>
}