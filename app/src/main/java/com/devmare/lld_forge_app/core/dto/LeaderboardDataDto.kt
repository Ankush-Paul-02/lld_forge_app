package com.devmare.lld_forge_app.core.dto

data class MentorDto(
    val id: Int,
    val name: String,
    val questionCount: Int,
)

data class LeaderboardDataDto(
    val data: List<MentorDto>,
)