package com.devmare.lld_forge_app.data.model

data class MentorshipBookingResponse(
    val orderId: String,
    val amount: Int,
    val currency: String,
    val sessionId: Int,
)