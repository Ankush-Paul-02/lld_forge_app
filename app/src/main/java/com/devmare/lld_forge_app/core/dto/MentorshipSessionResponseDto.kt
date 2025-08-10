package com.devmare.lld_forge_app.core.dto

data class MentorshipSessionResponseDto(
    val id: Int,
    val mentorId: Int,
    val mentorName: String,
    val mentorshipSessionStatus: String,
    val createdAt: Long,
    val scheduledAt: Long,
    val durationInMinutes: Int,
    val startedAt: Long?,
    val endedAt: Long?,
    val paymentVerified: Boolean,
    val meetingLink: String?,
    val orderId: String,
    val amount: Int,
    val paymentAt: Long?,
    val orderStatus: String,
)
