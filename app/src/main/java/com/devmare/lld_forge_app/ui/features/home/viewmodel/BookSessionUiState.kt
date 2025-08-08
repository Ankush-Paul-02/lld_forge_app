package com.devmare.lld_forge_app.ui.features.home.viewmodel

import com.devmare.lld_forge_app.data.model.MentorshipBookingResponse

sealed class BookSessionUiState {
    object Idle : BookSessionUiState()
    object Loading : BookSessionUiState()
    data class Success(val data: MentorshipBookingResponse) : BookSessionUiState()
    data class Error(val message: String) : BookSessionUiState()
}
