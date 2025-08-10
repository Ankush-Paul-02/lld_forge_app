package com.devmare.lld_forge_app.data.api

import com.devmare.lld_forge_app.core.dto.ApiResponse
import com.devmare.lld_forge_app.core.dto.MentorDto
import com.devmare.lld_forge_app.core.dto.MentorshipSessionResponseDto
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.data.model.MentorshipBookingResponse
import com.devmare.lld_forge_app.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("users/me")
    suspend fun fetchUserProfile(): ApiResponse<User>

    @GET("users/leaderboard/mentors")
    suspend fun fetchLeaderboardMentors(): ApiResponse<List<MentorDto>>

    @POST("users/mentorship-session/book")
    suspend fun bookMentorshipSession(
        @Body request: MentorshipBookingRequest,
    ): ApiResponse<MentorshipBookingResponse>

    @GET("users/mentorship-sessions")
    suspend fun fetchMentorshipSessions(): ApiResponse<List<MentorshipSessionResponseDto>>
}