package com.devmare.lld_forge_app.ui.features.auth.viewmodel

sealed class SignUpUIState {
    object Idle : SignUpUIState()
    object Loading : SignUpUIState()
    data class Success(val message: String) : SignUpUIState()
    data class Error(val error: String) : SignUpUIState()
}