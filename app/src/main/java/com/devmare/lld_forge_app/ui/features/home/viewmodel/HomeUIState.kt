package com.devmare.lld_forge_app.ui.features.home.viewmodel

import com.devmare.lld_forge_app.domain.model.User

sealed class HomeUIState {
    object Idle : HomeUIState()
    object Loading : HomeUIState()
    data class Success(val user: User) : HomeUIState()
    data class Error(val message: String) : HomeUIState()
    object SessionExpired : HomeUIState()
}
