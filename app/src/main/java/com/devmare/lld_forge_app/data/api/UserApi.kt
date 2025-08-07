package com.devmare.lld_forge_app.data.api

import com.devmare.lld_forge_app.core.dto.DefaultResponseDto
import com.devmare.lld_forge_app.core.dto.LeaderboardDataDto
import com.devmare.lld_forge_app.data.model.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("users/me")
    suspend fun fetechUserProfile(): UserResponse

    @GET("users/leaderboard/mentors")
    suspend fun fetchLeaderboardMentors(): DefaultResponseDto<LeaderboardDataDto>
}