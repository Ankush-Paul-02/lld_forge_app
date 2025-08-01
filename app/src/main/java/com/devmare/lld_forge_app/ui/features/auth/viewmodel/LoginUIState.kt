package com.devmare.lld_forge_app.ui.features.auth.viewmodel

data class LoginUIState(
    val isLoading: Boolean = false,
    val accessToken: String? = null,
    val error: String? = null,
)