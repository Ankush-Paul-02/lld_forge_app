package com.devmare.lld_forge_app.ui.features.home.viewmodel

import com.devmare.lld_forge_app.domain.model.User

sealed class UserUIState {
    object Idle : UserUIState()
    object Loading : UserUIState()
    data class Success(val user: User) : UserUIState()
    data class Error(val message: String) : UserUIState()
}