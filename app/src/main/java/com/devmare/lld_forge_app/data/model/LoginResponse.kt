package com.devmare.lld_forge_app.data.model

data class LoginResponse(
    val status: String,
    val data: AuthDataWrapper,
    val message: String,
)

data class AuthDataWrapper(
    val data: TokenData,
)

data class TokenData(
    val accessToken: String,
    val refreshToken: String,
)