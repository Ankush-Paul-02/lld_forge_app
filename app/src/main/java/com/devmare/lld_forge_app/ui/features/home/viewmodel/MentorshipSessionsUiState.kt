package com.devmare.lld_forge_app.ui.features.home.viewmodel

import com.devmare.lld_forge_app.core.dto.MentorshipSessionResponseDto

sealed class MentorshipSessionsUiState {
    object Idle : MentorshipSessionsUiState()
    object Loading : MentorshipSessionsUiState()
    data class Success(val sessions: List<MentorshipSessionResponseDto>) :
        MentorshipSessionsUiState()

    data class Error(val message: String) : MentorshipSessionsUiState()
}
