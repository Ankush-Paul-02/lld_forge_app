package com.devmare.lld_forge_app.data.model

data class MentorshipBookingRequest(
    val amount: Int,
    val currency: String = "INR",
    val receiverId: Int,
    val message: String = "Request for mentorship session!",
    val scheduledAt: Long,
    val durationInMinutes: Int
)
